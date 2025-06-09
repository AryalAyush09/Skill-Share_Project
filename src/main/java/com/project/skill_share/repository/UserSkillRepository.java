package com.project.skill_share.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.skill_share.entities.User_Skill;

public interface UserSkillRepository extends JpaRepository<User_Skill, Long> {
       
}
