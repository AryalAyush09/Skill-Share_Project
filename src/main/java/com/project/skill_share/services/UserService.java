package com.project.skill_share.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.skill_share.DTO.AdminUserDto;
import com.project.skill_share.DTO.UserResponseDto;
import com.project.skill_share.DTO.UserSkillResponseDTO;
import com.project.skill_share.DTO.UserUpdateDto;
import com.project.skill_share.GlobalErrorHandler.AlreadyExistsException;
import com.project.skill_share.GlobalErrorHandler.UserNotFoundException;
import com.project.skill_share.entities.User;
import com.project.skill_share.entities.User_Skill;
import com.project.skill_share.enums.SkillType;
import com.project.skill_share.helperClass.UserToDto;
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
	
	public UserService(UserRepository userRepo, UserSkillRepository userSkillRepo) {
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
	
	public ApiResponse<UserUpdateDto> updateUser(Long userId, UserUpdateDto dto){
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User Not Found"));
		
		if (dto.getUserName() != null && !dto.getUserName().equals(user.getUsername())) {
		    if (userRepo.existsByUsername(dto.getUserName())) {
		        throw new AlreadyExistsException("Username already taken");
		    }
		    user.setUsername(dto.getUserName());
		}
		if(dto.getContactNumber() != null) user.setContactNumber(dto.getContactNumber());
		
		if (dto.getGitHub() != null)
		    user.setGitHub(dto.getGitHub());
		
		userRepo.save(user);
		
		UserUpdateDto updateDto = new UserUpdateDto();
		updateDto.setContactNumber(user.getContactNumber());
		updateDto.setUserName(user.getUsername());
		
		return new ApiResponse<>(true , "User Data Updated",updateDto);
	}
}
