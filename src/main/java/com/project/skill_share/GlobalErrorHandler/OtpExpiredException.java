package com.project.skill_share.GlobalErrorHandler;

public class OtpExpiredException extends RuntimeException {
	public OtpExpiredException(String message) {
		super(message);
	}
}
