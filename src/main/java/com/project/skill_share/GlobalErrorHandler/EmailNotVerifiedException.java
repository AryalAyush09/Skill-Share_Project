package com.project.skill_share.GlobalErrorHandler;

public class EmailNotVerifiedException extends RuntimeException {
	public EmailNotVerifiedException(String message) {
		super(message);
	}
}
