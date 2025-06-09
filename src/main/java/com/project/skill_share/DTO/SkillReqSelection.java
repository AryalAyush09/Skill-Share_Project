package com.project.skill_share.DTO;

import java.util.List;

public class SkillReqSelection {
    private List<CategorySkillDTO> haveSkills;
    private List<CategorySkillDTO> needSkills;
    
	public List<CategorySkillDTO> getHaveSkills() {
		return haveSkills;
	}
	
	public void setHaveSkills(List<CategorySkillDTO> haveSkills) {
		this.haveSkills = haveSkills;
	}
	
	public List<CategorySkillDTO> getNeedSkills() {
		return needSkills;
	}
	
	public void setNeedSkills(List<CategorySkillDTO> needSkills) {
		this.needSkills = needSkills;
	}
}

