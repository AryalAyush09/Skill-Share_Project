package com.project.skill_share.DTO;

public class MatchedUserDto {
  private Long Id;
  private String userName;
  private String profileImages;
  
public Long getId() {
	return Id;
}
public void setId(Long id) {
	Id = id;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getProfileImages() {
	return profileImages;
}
public void setProfileImages(String profileImages) {
	this.profileImages = profileImages;
}
  
  
}
