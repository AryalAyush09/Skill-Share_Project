package com.project.skill_share.DTO;

public class LoginResponseDto {
	
	    private String token;
	    private LoginUserDto user;

	    public LoginResponseDto(String token, LoginUserDto user) {
	        this.token = token;
	        this.user = user;
	    }

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public LoginUserDto getUser() {
			return user;
		}

		public void setUser(LoginUserDto user) {
			this.user = user;
		}
	}
