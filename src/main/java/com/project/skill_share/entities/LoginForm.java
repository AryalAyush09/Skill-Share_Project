package com.project.skill_share.entities;

public class LoginForm {
     private String email;
     private String password;
//     private String userName;
     
     public LoginForm(String email ,String password) {
    	 this.email=email;
 		this.password = password;
     }
    public LoginForm() {
     }
     
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
//	public String getUserName() {
//		return userName;
//	}
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
}
