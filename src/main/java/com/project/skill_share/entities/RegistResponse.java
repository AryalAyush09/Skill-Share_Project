package com.project.skill_share.entities;

public class RegistResponse {
    private boolean success;
    private String message;
    
    
   public RegistResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
   
    public RegistResponse() {
    	
    }

    public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
