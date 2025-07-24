package com.project.skill_share.DTO;
import java.time.LocalDateTime;

public class RatingResponseDto {
	

    private String raterName;
    private String raterProfileImageUrl;
    private int stars;
    private String feedback;
    private LocalDateTime createdAt;

    
    public RatingResponseDto(String raterName, String raterProfileImageUrl, int stars, String feedback, LocalDateTime createdAt) {
        this.raterName = raterName;
        this.raterProfileImageUrl = raterProfileImageUrl;
        this.stars = stars;
        this.feedback = feedback;
        this.createdAt = createdAt;
    }


	public String getRaterName() {
		return raterName;
	}


	public void setRaterName(String raterName) {
		this.raterName = raterName;
	}


	public String getRaterProfileImageUrl() {
		return raterProfileImageUrl;
	}


	public void setRaterProfileImageUrl(String raterProfileImageUrl) {
		this.raterProfileImageUrl = raterProfileImageUrl;
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

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}