package com.project.skill_share.DTO;

public class RatingRequestDto {
    private Long sessionId;
    private Long rateeId;
    private int stars;
    private String feedback;
    
    
	public Long getSessionId() {
		return sessionId;
	}
	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}
	public Long getRateeId() {
		return rateeId;
	}
	public void setRateeId(Long rateeId) {
		this.rateeId = rateeId;
	}
	public int getStars() {
		return stars;
	}
	public void setStars(int stars) {
		this.stars = stars;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
    
}
