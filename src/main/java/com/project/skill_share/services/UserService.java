package com.project.skill_share.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.skill_share.DTO.AdminUserDto;
import com.project.skill_share.DTO.SocialLinkDto;
import com.project.skill_share.DTO.UserResponseDto;
import com.project.skill_share.DTO.UserSkillResponseDTO;
import com.project.skill_share.DTO.UserUpdateDto;
import com.project.skill_share.GlobalErrorHandler.AlreadyExistsException;
import com.project.skill_share.GlobalErrorHandler.UserNotFoundException;
import com.project.skill_share.entities.SocialLink;
import com.project.skill_share.entities.User;
import com.project.skill_share.entities.UserCV;
import com.project.skill_share.entities.User_Skill;
import com.project.skill_share.enums.SkillType;
import com.project.skill_share.helperClass.UserToDto;
import com.project.skill_share.repository.SocialLinkRepository;
import com.project.skill_share.repository.UserRepository;
import com.project.skill_share.repository.UserSkillRepository;
import com.project.skill_share.response.ApiResponse;
import com.project.skill_share.response.GenericResponse;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
   
	private final UserRepository userRepo;
	private final UserSkillRepository userSkillRepo;
	public UserService(UserRepository userRepo, UserSkillRepository userSkillRepo, SocialLinkRepository socialLinkRepo) {
		this.userRepo = userRepo;
		this.userSkillRepo = userSkillRepo;
	}
	
	public GenericResponse getUserById(Long userId) {
		User user = userRepo.findById(userId)
		  .orElseThrow(() -> new UserNotFoundException("User not found!"));
		
		UserResponseDto dto = UserToDto.toDto(user);
		
		return new GenericResponse(true ,"User Data Fetched Successfully", dto);
	}
	
	public ApiResponse<?> getAllUser(){
		List<User> user = userRepo.findAll();
		if(user.isEmpty()) {
			throw new UserNotFoundException("User List is Empty");
		}
		
		List<AdminUserDto> dto = UserToDto.toAdminUserDtoList(user);
		 return new ApiResponse<>(true, "Users Fetched Succcesffully",dto);
	}
	
	public ApiResponse<UserSkillResponseDTO> getUserSkill(Long userId){
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found"));
		
		List<User_Skill> allUserSkills = userSkillRepo.findByUser(user);
		
		Map<String, List<String>> haveSkills = allUserSkills.stream()
				.filter(skill -> skill.getType().equals(SkillType.HAVE))
				.collect(Collectors.groupingBy(skill -> skill.getSkill().getCategory().getCategoryName(),
				Collectors.mapping(skill -> skill.getSkill().getSkillName(), Collectors.toList())));
		
		Map<String, List<String>> needSkills = allUserSkills.stream()
				.filter(skill -> skill.getType().equals(SkillType.NEED))
				.collect(Collectors.groupingBy(skill -> skill.getSkill().getCategory().getCategoryName(),
				Collectors.mapping(skill -> skill.getSkill().getSkillName(),Collectors.toList())));
		
		UserSkillResponseDTO dto = new UserSkillResponseDTO();
		dto.setHaveSkillsByCategory(haveSkills);
		dto.setNeedSkillsByCategory(needSkills);
	    return new ApiResponse<>(true, "Fetched User Skill Successfully!", dto);
	}
	
//	public ApiResponse<UserUpdateDto> updateUser(Long userId, UserUpdateDto dto){
//		User user = userRepo.findById(userId)
//				.orElseThrow(() -> new UserNotFoundException("User Not Found"));
//		
//		if (dto.getUserName() != null && !dto.getUserName().equals(user.getUsername())) {
//		    if (userRepo.existsByUsername(dto.getUserName())) {
//		        throw new AlreadyExistsException("Username already taken");
//		    }
//		    user.setUsername(dto.getUserName());
//		}
//		if(dto.getContactNumber() != null) user.setContactNumber(dto.getContactNumber());
//		
//		if (dto.getGitHub() != null)
//		    user.setGitHub(dto.getGitHub());
//		
//		userRepo.save(user);
//		
//		UserUpdateDto updateDto = new UserUpdateDto();
//		updateDto.setContactNumber(user.getContactNumber());
//		updateDto.setUserName(user.getUsername());
//		
//		return new ApiResponse<>(true , "User Data Updated",updateDto);
//	}
	
	@Transactional
	public ApiResponse<UserUpdateDto> updateUserProfile(Long userId, UserUpdateDto dto) {
	    User user = userRepo.findById(userId)
	            .orElseThrow(() -> new UserNotFoundException("User Not Found"));

	    // Update username with uniqueness check
	    if (dto.getUserName() != null && !dto.getUserName().equals(user.getUsername())) {
	        if (userRepo.existsByUsername(dto.getUserName())) {
	            throw new AlreadyExistsException("Username already taken");
	        }
	        user.setUsername(dto.getUserName());
	    }
	    
      if(dto.getFullName() != null) {
	   user.setFullName(dto.getFullName());
      }
      
	    if (dto.getContactNumber() != null) {
	        user.setContactNumber(dto.getContactNumber());
	    }

	    // Update CV URL
//	    if (dto.getCvUrl() != null) {
//	        if (user.getUserCV() == null) {
//	            UserCV cv = new UserCV();
//	            cv.setCvUrl(dto.getCvUrl());
//	            cv.setUser(user);
//	            user.setUserCV(cv);
//	        } else {
//	            user.getUserCV().setCvUrl(dto.getCvUrl());
//	        }
//	    }

	    // Update social links if provided
	    if (dto.getSocialLinks() != null) {
	        Map<String, SocialLink> currentLinks = user.getSocialLinks()
	            .stream()
	            .collect(Collectors.toMap(SocialLink::getPlatform, link -> link, (a, b) -> b));

	        for (SocialLinkDto dtoLink : dto.getSocialLinks()) {
	        	
	            if (currentLinks.containsKey(dtoLink.getPlatform())) {
	                // Update existing link
	                currentLinks.get(dtoLink.getPlatform()).setUrl(dtoLink.getUrl());
	            } else {
	                // Create new link
	                SocialLink newLink = new SocialLink(dtoLink.getPlatform(), dtoLink.getUrl(), user);
	                user.getSocialLinks().add(newLink);
	            }
	        }
	    }

	    userRepo.save(user);

	    // Refresh user to get updated social links
	    User updatedUser = userRepo.findById(userId)
	        .orElseThrow(() -> new UserNotFoundException("User Not Found"));

	    UserUpdateDto responseDto = new UserUpdateDto();
	    responseDto.setUserName(updatedUser.getUsername());
	    responseDto.setFullName(updatedUser.getFullName());
	    responseDto.setContactNumber(updatedUser.getContactNumber());
	    
//	    if (updatedUser.getUserCV() != null) {
//	        responseDto.setCvUrl(updatedUser.getUserCV().getCvUrl());
//	    }

	    if (updatedUser.getSocialLinks() != null) {
	        List<SocialLinkDto> links = updatedUser.getSocialLinks().stream()
	            .map(link -> new SocialLinkDto(link.getPlatform(), link.getUrl()))
	            .collect(Collectors.toList());
	        responseDto.setSocialLinks(links);
	    }

	    return new ApiResponse<>(true, "User profile updated", responseDto);
	}

	  @Transactional
	    public void addOrUpdateSocialLinks(Long userId, List<SocialLinkDto> linksDto) {
	        User user = userRepo.findById(userId)
	                .orElseThrow(() -> new UserNotFoundException("User not found"));

	        Map<String, SocialLink> existingLinks = user.getSocialLinks().stream()
	                .collect(Collectors.toMap(SocialLink::getPlatform, link -> link, (a, b) -> b));

	        for (SocialLinkDto dto : linksDto) {
	            if (existingLinks.containsKey(dto.getPlatform())) {
	                existingLinks.get(dto.getPlatform()).setUrl(dto.getUrl());
	            } else {
	                SocialLink newLink = new SocialLink(dto.getPlatform(), dto.getUrl(), user);
	                user.getSocialLinks().add(newLink);
	            }
	        }

	        userRepo.save(user);
	    }
	
	public List<SocialLinkDto> getSocialLinks(Long userId) {
	    User user = userRepo.findById(userId)
	        .orElseThrow(() -> new UserNotFoundException("User not found"));

	    return user.getSocialLinks().stream()
	        .map(link -> new SocialLinkDto(link.getPlatform(), link.getUrl()))
	        .collect(Collectors.toList());
	}

	@Transactional
	public void deleteSocialLink(Long userId, String platform) {
	    User user = userRepo.findById(userId)
	        .orElseThrow(() -> new UserNotFoundException("User not found"));

	    user.getSocialLinks().removeIf(link -> link.getPlatform().equalsIgnoreCase(platform));

	    userRepo.save(user);
	}
}
