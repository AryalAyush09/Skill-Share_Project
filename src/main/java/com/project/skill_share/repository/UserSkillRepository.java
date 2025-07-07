package com.project.skill_share.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.skill_share.entities.Skill;
import com.project.skill_share.enums.SkillType;
import com.project.skill_share.entities.User;
import com.project.skill_share.entities.User_Skill;

public interface UserSkillRepository extends JpaRepository<User_Skill, Long> {
	   boolean existsByUserAndSkillAndType(User user, Skill skill, SkillType type);
       List<User_Skill> findByUser(User user);
}
