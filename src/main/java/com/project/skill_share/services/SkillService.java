package com.project.skill_share.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.project.skill_share.DTO.SkillReqDTO;
import com.project.skill_share.GlobalErrorHandler.AlreadyExistsException;
import com.project.skill_share.GlobalErrorHandler.ResourceNotFoundException;
import com.project.skill_share.entities.Category;
import com.project.skill_share.entities.Skill;
import com.project.skill_share.repository.SkillRepository;
import com.project.skill_share.response.GenericResponse;

@Service
public class SkillService {

	private final SkillRepository skillRepo;
	private final CategoryService catService;
	
	public SkillService(SkillRepository skillRepo, CategoryService catService) {
		this.skillRepo = skillRepo;
		this.catService = catService;
	}
	
	public GenericResponse getAllSkill() {
		List<Skill> Skills = skillRepo.findAll();
		if(Skills.isEmpty()) {
			throw new ResourceNotFoundException("Skill Not Found");	
		}
		return new GenericResponse(true, "Fetched Successfully!!",Skills);
	}
	
	public GenericResponse addSkill(SkillReqDTO dto) {
		 if(skillRepo.existsBySkillName(dto.getSkillName())) {
			  throw new AlreadyExistsException("Skill already exists!");
		 }
		 Category category = catService.findById(dto.getCategoryId())
			        .orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
		 
//		   System.out.println("Found category: " + category.getCategoryName());
		   
		    Skill skill = new Skill();
		    skill.setSkillName(dto.getSkillName());
		    skill.setCategory(category);
	     Skill saved = skillRepo.save(skill);
		 return new GenericResponse(true, "Added Succesfully!!",saved);	
	}
}
