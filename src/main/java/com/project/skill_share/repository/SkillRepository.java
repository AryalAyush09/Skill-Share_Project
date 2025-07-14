package com.project.skill_share.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.skill_share.entities.Category;
import com.project.skill_share.entities.Skill;

@Repository 
public interface SkillRepository extends JpaRepository<Skill, Long> {
	boolean existsBySkillName(String skillname);
//	  List<Skill> findBySkillName(String skillname);
	  Optional<Skill> findBySkillName(String skillName);
	  List<Skill>findByCategory(Category category);
}
