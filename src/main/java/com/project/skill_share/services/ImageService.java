package com.project.skill_share.services;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.project.skill_share.DTO.ImageDto;
import com.project.skill_share.GlobalErrorHandler.ResourceNotFoundException;
import com.project.skill_share.GlobalErrorHandler.UserNotFoundException;
import com.project.skill_share.entities.User;
import com.project.skill_share.entities.UserCV;
import com.project.skill_share.entities.UserImage;
import com.project.skill_share.enums.ImageType;
import com.project.skill_share.repository.UserImageRepository;
import com.project.skill_share.repository.UserRepository;
import com.project.skill_share.response.ApiResponse;
import java.io.IOException;

@Service
public class ImageService {

	private final Cloudinary cloudinary;
	private final UserRepository userRepo;
	private final UserImageRepository userImageRepo;

	public ImageService(Cloudinary cloudinary, UserRepository userRepo,
	                    UserImageRepository userImageRepo) {
		this.cloudinary = cloudinary;
		this.userRepo = userRepo;
		this.userImageRepo = userImageRepo;
	}

	public ApiResponse<ImageDto> uploadImage(MultipartFile file, Long userId, ImageType imgType) {
		validateImage(file);
		User user = getUserById(userId);

		UserImage userImg = uploadToCloudinary(file, user, imgType);

		if (imgType == ImageType.PROFILE) {
			user.setProfileImage(userImg);
			userRepo.save(user);
		}

		ImageDto dto = toImageDto(userImg);
		return new ApiResponse<>(true, "Image uploaded successfully", dto);
	}

	public ApiResponse<ImageDto> replaceProfileImage(MultipartFile file, Long userId) {
		validateImage(file);
		User user = getUserById(userId);
		
		UserImage oldImg = user.getProfileImage();
		if (oldImg != null) {
			try {
				cloudinary.uploader().destroy(oldImg.getPublicId(), Map.of());
				userImageRepo.delete(oldImg);
			} catch (IOException e) {
				throw new RuntimeException("Failed to delete old profile image", e);
			}
		}

		UserImage newImg = uploadToCloudinary(file, user, ImageType.PROFILE);
		user.setProfileImage(newImg);
		userRepo.save(user);

		ImageDto dto = toImageDto(newImg);
		return new ApiResponse<>(true, "Profile image replaced successfully", dto);
	}

    public ApiResponse<String> uploadCV(MultipartFile file, Long userId){
    	validatePdf(file);
    	User user =getUserById(userId);
  
    	 String folder = "user-cvs";
         String publicId = "cv_" + user.getId() + "_" + System.currentTimeMillis();
         
         try {
        	 Map uploadResult =cloudinary.uploader().upload(file.getBytes(), Map.of(
        			 "folder", folder,"public_id", publicId));
        	 
           String cvUrl = uploadResult.get("secure_url").toString();
           String cvPublicId = uploadResult.get("public_id").toString();

           UserCV userCV = new UserCV();
           userCV.setCvUrl(cvUrl);
           userCV.setPublicId(cvPublicId);
           userCV.setUser(user);

           user.setUserCV(userCV);
           
        	 userRepo.save(user);
        	 return new ApiResponse<>(true, "CV uploaded Successfully!", cvUrl);
        	 
         }catch(IOException e) {
        	 throw new RuntimeException("Failed to upload the CV");
         }  
    }

    public ApiResponse<String> replaceCV(MultipartFile file, Long userId){
	   validatePdf(file);
	   User user = getUserById(userId);
	   
	   UserCV oldUserCV = user.getUserCV();
	   
	   if(oldUserCV != null) {
		   
		   String oldPublicId = oldUserCV.getPublicId();
		   
		   try {
			   cloudinary.uploader().destroy(oldPublicId, Map.of());
		   } catch(IOException e) {
			   throw new RuntimeException("Falied to delete old CV", e);
		   }
		   
		   user.setUserCV(null);
	   }
	   
	   String folder = "users_cvs";
	   String publicId = "cv_" + user.getId() + "_" + System.currentTimeMillis();
	   
	   try {
		   Map uploadResult = cloudinary.uploader().upload(file.getBytes(),Map.of(
				   "folder",folder ,
				   "public_id", publicId));
		   String newCvUrl = uploadResult.get("secure_url").toString();
		   String newPublicId = uploadResult.get("public_id").toString();
		   
		   UserCV newUserCV = new UserCV();
	        newUserCV.setCvUrl(newCvUrl);
	        newUserCV.setPublicId(newPublicId);
	        newUserCV.setUser(user);

	        user.setUserCV(newUserCV);

	        userRepo.save(user); 
	
		   return new ApiResponse<>(true, "CV updated Successfully", newCvUrl);
		   
	   }catch (IOException e) {
		   throw new RuntimeException("Failed to upload a new CV", e);
	   }
   }
    
//    public ApiResponse<?> deletePhoto(Long userId){
//    	User user = getUserById(userId);
//         
//    	UserImage profileImage = user.getProfileImage()
//    			.orElseThrow(() -> new ResourceNotFoundException("Profile Image Not Found"));
//    	
//    	String publicId = profileImage.getPublicId();
//    	try {
//    		cloudinary.uploader().destroy(publicId, Map.of());
//    	} catch (IOException e) {
//    		throw new RuntimeException("Failed to delete the photo");
//    	}
//    	
//    	return new ApiResponse<>(true, "Successfully deleted the user image", null);
//    }
//    
	  private void validateImage(MultipartFile file) {
	        validateFile(file, new String[]{"image/jpeg", "image/jpg", "image/png"}, "Only JPEG and PNG images are allowed");
	    }

	  private void validatePdf(MultipartFile file) {
	        validateFile(file, new String[]{"application/pdf"}, "Only PDF files are allowed");
	    }

	  private void validateFile(MultipartFile file, String[] allowedTypes, String errorMessage) {
	        if (file == null || file.isEmpty())
	        	throw new ResourceNotFoundException("No file provided");
	        
	        String contentType = file.getContentType();
	        boolean valid = false;
	        
	        for (String type : allowedTypes) {
	            if (type.equalsIgnoreCase(contentType)) {
	                valid = true;
	                break;
	            }
	        }
	        if (!valid) throw new IllegalArgumentException(errorMessage);
	    }
	    
	private User getUserById(Long userId) {
		return userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found"));
	}

	private UserImage uploadToCloudinary(MultipartFile file, User user, ImageType imgType) {
		try {
			Map<String, Object> options = Map.of(
					"folder", "user-images/" + imgType.name().toLowerCase(),
					"public_id", "user_" + user.getId() + "_" + System.currentTimeMillis()
			);
			Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);

			UserImage userImg = new UserImage();
			userImg.setImageUrl(uploadResult.get("secure_url").toString());
			userImg.setPublicId(uploadResult.get("public_id").toString());
			userImg.setUser(user);
			userImg.setImageType(imgType);
			return userImageRepo.save(userImg);
		} catch (IOException e) {
			throw new RuntimeException("Failed to upload image", e);
		}
	}

	private ImageDto toImageDto(UserImage userImg) {
		ImageDto dto = new ImageDto();
		dto.setImageUrl(userImg.getImageUrl());
		dto.setPublicId(userImg.getPublicId());
		dto.setImageType(userImg.getImageType());
		dto.setCreatedAt(userImg.getCreatedAt());
		return dto;
	}
}

