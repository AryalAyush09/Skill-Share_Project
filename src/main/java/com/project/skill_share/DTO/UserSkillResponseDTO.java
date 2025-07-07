package com.project.skill_share.DTO;

import java.util.List;
import java.util.Map;

public class UserSkillResponseDTO {
    private Map<String, List<String>> haveSkillsByCategory;
    private Map<String, List<String>> needSkillsByCategory;

    public Map<String, List<String>> getHaveSkillsByCategory() {
        return haveSkillsByCategory;
    }

    public void setHaveSkillsByCategory(Map<String, List<String>> haveSkillsByCategory) {
        this.haveSkillsByCategory = haveSkillsByCategory;
    }

    public Map<String, List<String>> getNeedSkillsByCategory() {
        return needSkillsByCategory;
    }

    public void setNeedSkillsByCategory(Map<String, List<String>> needSkillsByCategory) {
        this.needSkillsByCategory = needSkillsByCategory;
    }
}
