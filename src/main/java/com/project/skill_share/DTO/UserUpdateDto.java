package com.project.skill_share.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserUpdateDto {
	
  @NotBlank(message = "Username is required")
  @Size(min = 3, max = 20, message = "Username must be 3 to 20 characters")
  private String userName;
  
  @NotBlank(message = "ContactNumber is required")
  private String contactNumber;

  private String gitHub;
  private String cvUrl; 

  
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
public String getGitHub() {
	return gitHub;
}
public void setGitHub(String gitHub) {
	this.gitHub = gitHub;
}
public String getCvUrl() {
	return cvUrl;
}
public void setCvUrl(String cvUrl) {
	this.cvUrl = cvUrl;
} 

}
