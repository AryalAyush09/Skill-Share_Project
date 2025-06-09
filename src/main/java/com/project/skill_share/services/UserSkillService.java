package com.project.skill_share.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.skill_share.DTO.CategorySkillDTO;
import com.project.skill_share.DTO.SkillReqSelection;
import com.project.skill_share.GlobalErrorHandler.ResourceNotFoundException;
import com.project.skill_share.entities.Category;
import com.project.skill_share.entities.Skill;
import com.project.skill_share.entities.SkillType;
import com.project.skill_share.entities.User;
import com.project.skill_share.entities.User_Skill;
import com.project.skill_share.repository.CategoryRepository;
import com.project.skill_share.repository.SkillRepository;
import com.project.skill_share.repository.UserSkillRepository;
import com.project.skill_share.response.GenericResponse;

@Service
public class UserSkillService {

    @Autowired
    private UserSkillRepository userSkillRepo;

    @Autowired
    private SkillRepository skillRepo;

    @Autowired
    private CategoryRepository catRepo;

    public GenericResponse addUserSkills(SkillReqSelection skillRequest, CategorySkillDTO catskilldto, User user) {

        // Save HAVE skills
        for (CategorySkillDTO dto : skillRequest.getHaveSkills()) {
            saveUserSkill(dto, SkillType.HAVE, user);
        }

        // Save NEED skills
        for (CategorySkillDTO dto : skillRequest.getNeedSkills()) {
            saveUserSkill(dto, SkillType.NEED, user);
        }

        return new GenericResponse(true, "User skills saved successfully", null);
    }

    //  method
    private void saveUserSkill(CategorySkillDTO dto, SkillType type, User user) {
        String skillName = dto.getSkillName();
        String categoryName = dto.getCategoryName();

        //category exists ?
        Category category = catRepo.findByCategoryName(categoryName)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryName));

        // skill exists  ?
        Skill skill = skillRepo.findBySkillName(skillName)
                .orElseThrow(() -> new ResourceNotFoundException("Skill not found: " + skillName));

        // is skill belongs to  category
        if (!skill.getCategory().getCategoryName().equals(categoryName)) {
            throw new IllegalArgumentException("Skill " + skillName + " does not belong to category " + categoryName);
        }

        // Save user-skill entry
        User_Skill userSkill = new User_Skill();
        userSkill.setUserName(user);
        userSkill.setSkillName(skill);
        userSkill.setCategoryName(category);
        userSkill.setType(type);

        userSkillRepo.save(userSkill);
    }
}
