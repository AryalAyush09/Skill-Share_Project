package com.project.skill_share.GlobalErrorHandler;

public class InvalidOtpException extends RuntimeException {
	public InvalidOtpException(String message) {
		super(message);
	}
}
