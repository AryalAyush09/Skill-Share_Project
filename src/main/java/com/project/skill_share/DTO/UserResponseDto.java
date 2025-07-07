package com.project.skill_share.DTO;

import java.util.List;

public class UserResponseDto {
	
  private Long id;
  
  private String userName;
  
  private String email;
  
  private String contactNumber;
  
  private String cvUrl;
  
  private List<ImageDto> images;	

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public String getContactNumber() {
	return contactNumber;
}

public void setContactNumber(String contactNumber) {
	this.contactNumber = contactNumber;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public List<ImageDto> getImages() {
	return images;
}

public String getCvUrl() {
	return cvUrl;
}

public void setCvUrl(String cvUrl) {
	this.cvUrl = cvUrl;
}

public void setImages(List<ImageDto> images) {
	this.images = images;
}

}
