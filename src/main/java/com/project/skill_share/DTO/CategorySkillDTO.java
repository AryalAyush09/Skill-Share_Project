package com.project.skill_share.DTO;

import jakarta.validation.constraints.NotBlank;

public class CategorySkillDTO {
	
	@NotBlank
    private String categoryName;
	@NotBlank
    private String skillName;
    
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
}
