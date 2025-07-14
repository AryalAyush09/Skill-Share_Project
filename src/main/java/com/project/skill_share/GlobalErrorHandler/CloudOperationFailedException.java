package com.project.skill_share.GlobalErrorHandler;

public class CloudOperationFailedException extends RuntimeException {
	public CloudOperationFailedException(String message) {
		super(message);
	}
}
