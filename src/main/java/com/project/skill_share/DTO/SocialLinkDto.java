package com.project.skill_share.DTO;

import jakarta.validation.constraints.NotBlank;

public class SocialLinkDto {

    @NotBlank(message = "Platform is required")
    private String platform;

    @NotBlank(message = "URL is required")
    private String url;

  
public SocialLinkDto() {
	
}
    
    public SocialLinkDto(String platform, String url) {
		this.platform = platform;
		this.url = url;
	}

	public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
