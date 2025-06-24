package com.project.skill_share.GlobalErrorHandler;

public class UserNotFoundException extends RuntimeException {
	    public UserNotFoundException(String message) {
	        super(message);
	    }
	}
