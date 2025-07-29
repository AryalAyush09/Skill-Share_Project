package com.project.skill_share.DTO;

import com.project.skill_share.enums.EmailTYPE;

public class AdminUserDto {
  private Long id;
  private String username;
  private  String fullName;
  private String contactNumber;
  private String email;
  private EmailTYPE emailStatus;
  private String profilePhoto;
  
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}

public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
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
public EmailTYPE getEmailStatus() {
	return emailStatus;
}
public void setEmailStatus(EmailTYPE emailStatus) {
	this.emailStatus = emailStatus;
}
public String getProfilePhoto() {
	return profilePhoto;
}
public void setProfilePhoto(String profilePhoto) {
	this.profilePhoto = profilePhoto;
}
public String getFullName() {
	return fullName;
}
public void setFullName(String fullName) {
	this.fullName = fullName;
}
  
  
}
