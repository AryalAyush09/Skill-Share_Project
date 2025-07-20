package com.project.skill_share.DTO;

public class MatchResponseDto {
    private Long requesterUserId;
    private String response;
    
	public Long getRequesterUserId() {
		return requesterUserId;
	}
	public void setRequesterUserId(Long requesterUserId) {
		this.requesterUserId = requesterUserId;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	} 
}

