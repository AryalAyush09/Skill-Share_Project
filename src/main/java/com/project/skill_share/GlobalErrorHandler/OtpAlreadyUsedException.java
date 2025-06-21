package com.project.skill_share.GlobalErrorHandler;

public class OtpAlreadyUsedException extends RuntimeException{
	public OtpAlreadyUsedException(String message) {
		super(message);
	}

}
