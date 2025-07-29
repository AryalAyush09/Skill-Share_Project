package com.project.skill_share.DTO;

import com.project.skill_share.enums.MatchStatus;

public class MatchResponseFullDto {
    private Long id;
    private Long currentUserId;
    private Long otherUserId;
    private float matchingScore;
    private MatchStatus matchStatus;
    
    
	public MatchResponseFullDto(Long id, Long currentUserId, Long otherUserId, float matchingScore,
			MatchStatus matchStatus) {
	
		this.id = id;
		this.currentUserId = currentUserId;
		this.otherUserId = otherUserId;
		this.matchingScore = matchingScore;
		this.matchStatus = matchStatus;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getCurrentUserId() {
		return currentUserId;
	}


	public void setCurrentUserId(Long currentUserId) {
		this.currentUserId = currentUserId;
	}


	public Long getOtherUserId() {
		return otherUserId;
	}


	public void setOtherUserId(Long otherUserId) {
		this.otherUserId = otherUserId;
	}


	public float getMatchingScore() {
		return matchingScore;
	}


	public void setMatchingScore(float matchingScore) {
		this.matchingScore = matchingScore;
	}


	public MatchStatus getMatchStatus() {
		return matchStatus;
	}


	public void setMatchStatus(MatchStatus matchStatus) {
		this.matchStatus = matchStatus;
	}
}

