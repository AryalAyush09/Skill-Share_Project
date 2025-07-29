package com.project.skill_share.DTO;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserUpdateDto {
	
  @NotBlank(message = "Username is required")
  @Size(min = 3, max = 20, message = "Username must be 3 to 20 characters")
  private String userName;
  
  private String fullName;
  
  @NotBlank(message = "ContactNumber is required")
  private String contactNumber;

  private List<SocialLinkDto> socialLinks;
//  private String cvUrl; 

  
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

public List<SocialLinkDto> getSocialLinks() {
	return socialLinks;
}
public void setSocialLinks(List<SocialLinkDto> socialLinks) {
	this.socialLinks = socialLinks;
}
public String getFullName() {
	return fullName;
}
public void setFullName(String fullName) {
	this.fullName = fullName;
}

//public String getCvUrl() {
//	return cvUrl;
//}
//public void setCvUrl(String cvUrl) {
//	this.cvUrl = cvUrl;
//} 

}
