package com.project.skill_share.helperClass;

import java.util.List;
import java.util.stream.Collectors;

import com.project.skill_share.DTO.AdminUserDto;
import com.project.skill_share.DTO.ImageDto;
import com.project.skill_share.DTO.UserResponseDto;
import com.project.skill_share.entities.User;
import com.project.skill_share.enums.ImageType;

public class UserToDto {
	
  public static UserResponseDto toDto(User user) {
	  UserResponseDto dto = new UserResponseDto();
	  dto.setId(user.getId());
	  dto.setUserName(user.getUsername());
	  dto.setEmail(user.getEmail());
	  dto.setContactNumber(user.getContactNumber());
	  
	  dto.setCvUrl(user.getUserCV() != null ? user.getUserCV().getCvUrl() : null);
	  
	  List<ImageDto> images = user.getImages().stream().map(image -> {
		  ImageDto imgDto = new ImageDto();
		  imgDto.setImageUrl(image.getImageUrl());
		  imgDto.setPublicId(image.getPublicId());
		  imgDto.setImageType(image.getImageType());
		  imgDto.setCreatedAt(image.getCreatedAt());
		  return imgDto;
	  })
	  .toList();
	  
	  dto.setImages(images);
	  
	  return dto;
  }
  
  public static AdminUserDto toAdminUserDto(User user) {
	AdminUserDto auDto = new AdminUserDto();
	  auDto.setUsername(user.getUsername());
	  auDto.setId(user.getId());
	  auDto.setEmail(user.getEmail());
	  auDto.setContactNumber(user.getContactNumber());
	  auDto.setEmailStatus(user.getEmailStatus());
	  
	  if(user.getImages()!= null && !user.getImages().isEmpty()) {
		  user.getImages().stream().filter(img -> img.getImageType() == ImageType.PROFILE)
		  .findFirst().ifPresent(profileImg -> auDto.setProfilePhoto(profileImg.getImageUrl()));
	  }
	  return auDto;
  }
  
  public static List<AdminUserDto> toAdminUserDtoList(List<User> users){
	  return users.stream().map(user -> UserToDto.toAdminUserDto(user)).collect(Collectors.toList());
  }
}
