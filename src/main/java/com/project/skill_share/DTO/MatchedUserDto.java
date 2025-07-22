package com.project.skill_share.DTO;

public class MatchedUserDto {
  private Long Id;
  private String userName;
  private String profileImageUrl;
  
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
public String getProfileImageUrl() {
	return profileImageUrl;
}
public void setProfileImageUrl(String profileImageUrl) {
	this.profileImageUrl = profileImageUrl;
}

}
