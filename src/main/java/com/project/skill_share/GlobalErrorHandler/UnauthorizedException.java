package com.project.skill_share.GlobalErrorHandler;

public class UnauthorizedException extends RuntimeException{
	public UnauthorizedException(String message) {
		super(message);
	}
}
