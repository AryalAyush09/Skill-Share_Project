package com.project.skill_share.DTO;

public class MatchRequestDto {

    private Long targetUserId;
    private float matchingScore;
    
	public Long getTargetUserId() {
		return targetUserId;
	}
	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}
	public float getMatchingScore() {
		return matchingScore;
	}
	public void setMatchingScore(float matchingScore) {
		this.matchingScore = matchingScore;
	}

   
}
