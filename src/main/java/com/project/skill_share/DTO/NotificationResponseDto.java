package com.project.skill_share.DTO;

import java.time.LocalDateTime;

import com.project.skill_share.enums.MatchStatus;

public class NotificationResponseDto {
	    private Long id;
	    private Long userId;
	    private String message;
	    private Long senderId;
	    private MatchStatus status;
	    private LocalDateTime createdAt;
	  

	    public NotificationResponseDto(Long id, Long userId, String message, 
	    		Long senderId, LocalDateTime createdAt , MatchStatus status) {
	        this.id = id;
	        this.userId = userId;
	        this.message = message;
	        this.senderId = senderId;
	        this.status = status;
	        this.createdAt = createdAt;
	    }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public MatchStatus getStatus() {
			return status;
		}

		public void setStatus(MatchStatus status) {
			this.status = status;
		}

		public Long getSenderId() {
			return senderId;
		}

		public void setSenderId(Long senderId) {
			this.senderId = senderId;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}	

}
