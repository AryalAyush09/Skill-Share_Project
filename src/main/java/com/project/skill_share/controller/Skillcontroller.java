package com.project.skill_share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.skill_share.DTO.SkillReqDTO;
import com.project.skill_share.entities.Skill;
import com.project.skill_share.response.GenericResponse;
import com.project.skill_share.services.SkillService;

@RestController
@RequestMapping("/api")

public class Skillcontroller {
	@Autowired
	private SkillService skillService;
	
	@GetMapping("/get/skill")
		public ResponseEntity<GenericResponse> getSkill(Skill skill){
			return ResponseEntity.ok(skillService.getAllSkill());
		}
	
	@PostMapping("/add/skill")
	public ResponseEntity<GenericResponse> createSkill(@RequestBody SkillReqDTO skillDto) {
	    return ResponseEntity.ok(skillService.addSkill(skillDto));
	}

}
