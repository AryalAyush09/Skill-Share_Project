package com.project.skill_share.DTO;

public class NotificationRequestDto {

	    private Long receiverUserId;
	    private String message;
	    
		public Long getReceiverUserId() {
			return receiverUserId;
		}	
		public void setReceiverUserId(Long receiverUserId) {
			this.receiverUserId = receiverUserId;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
}
