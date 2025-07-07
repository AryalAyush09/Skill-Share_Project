package com.project.skill_share.DTO;

import com.project.skill_share.enums.Role;

public class LoginUserDto {

	    private Long id;
	    private String email;
	    private Role roles;

	    public LoginUserDto(Long id, String email, Role roles) {
	        this.id = id;
	        this.email = email;
	        this.roles = roles;
	    }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public Role getRoles() {
			return roles;
		}

		public void setRoles(Role roles) {
			this.roles = roles;
		}
	}

