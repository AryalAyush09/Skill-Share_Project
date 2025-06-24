package com.project.skill_share.GlobalErrorHandler;

public class InvalidCredentialsException extends RuntimeException{
   public InvalidCredentialsException(String message) {
	   super(message);
   }
}
