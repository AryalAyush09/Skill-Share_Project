package com.project.skill_share.GlobalErrorHandler;

public class EmailAlreadyVerifiedException extends RuntimeException {
	 public EmailAlreadyVerifiedException(String message) {
	        super(message);
	 }
}
