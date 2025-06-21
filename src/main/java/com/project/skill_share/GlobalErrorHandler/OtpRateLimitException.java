package com.project.skill_share.GlobalErrorHandler;

public class OtpRateLimitException extends RuntimeException{
	  public OtpRateLimitException(String message) {
	        super(message);
	    }
}
