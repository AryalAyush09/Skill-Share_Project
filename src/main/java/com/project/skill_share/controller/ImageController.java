package com.project.skill_share.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.skill_share.DTO.ImageDto;
import com.project.skill_share.enums.ImageType;
import com.project.skill_share.response.ApiResponse;
import com.project.skill_share.services.ImageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Image & CV Uploads", description = "Handles user photo and CV/PDF upload/updateand ao delete operations")
public class ImageController {
	
	private final ImageService imageService;
	
	 public ImageController(ImageService imageService) {
	        this.imageService = imageService;
	    }
	 
	   @Operation(summary = "Upload User Photo")
	    @PostMapping(value = "/images/upload", consumes = "multipart/form-data")
	     public ResponseEntity<ApiResponse<ImageDto>> uploadPhoto(@RequestPart("file") MultipartFile file,Authentication Auth,@RequestParam("imgType") ImageType imageType) {
	    	Long userId = Long.parseLong(Auth.getName());
		    ApiResponse<ImageDto> response = imageService.uploadImage(file, userId, imageType);
	      return ResponseEntity.ok(response);
	 }
	   
	  @Operation(summary = "Replace existing ProfilePhoto")
	  @PutMapping(value = "/profile-image/update",  consumes = "multipart/form-data")
	   public ResponseEntity<ApiResponse<ImageDto>> updateProfileImage(Authentication auth,@RequestParam("file") MultipartFile file) {
           Long userId = Long.parseLong(auth.getName());
	       ApiResponse<ImageDto> response = imageService.replaceProfileImage(file, userId);
	      return ResponseEntity.ok(response);
	   }
	  
	  @Operation(summary = "Upload User CV") 
      @PostMapping(value ="/upload/CV", consumes = "multipart/form-data")
       public ResponseEntity<ApiResponse<?>> uploadCV(@RequestPart("file") MultipartFile file ,Authentication auth){
		  Long userId = Long.parseLong(auth.getName());
	   return ResponseEntity.ok(imageService.uploadCV(file, userId));
    }
   
      @Operation(summary = "Replace existing CV")
      @PutMapping(value ="/update/CV", consumes = "multipart/form-data")
       public ResponseEntity<ApiResponse<?>> replaceCV(@RequestPart ("file") MultipartFile file, Authentication auth){
	   Long userId = Long.parseLong(auth.getName());
	   return ResponseEntity.ok(imageService.replaceCV(file, userId));
   }
      
      @Operation(summary = "Delete User Image")
      @DeleteMapping("/delete/images")
       public ResponseEntity<ApiResponse<?>> deleteImage(Authentication auth){
    	  Long userId = Long.parseLong(auth.getName());
    	  return ResponseEntity.ok(imageService.deletePhoto(userId));
      }
      
      @Operation(summary = "Delete User CV")
      @DeleteMapping("/delete/CV")
       public ResponseEntity<ApiResponse<?>> deleteCV(Authentication auth){
    	  Long userId  = Long.parseLong(auth.getName());
    	  return ResponseEntity.ok(imageService.deleteCV(userId));
      }
}
