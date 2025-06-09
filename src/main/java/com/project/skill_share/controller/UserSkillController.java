package com.project.skill_share.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.skill_share.DTO.SkillReqSelection;
import com.project.skill_share.GlobalErrorHandler.ResourceNotFoundException;
import com.project.skill_share.entities.User;
import com.project.skill_share.repository.UserRepository;
import com.project.skill_share.response.GenericResponse;
import com.project.skill_share.services.UserSkillService;


@RestController
@RequestMapping("/api")

public class UserSkillController {
	
	@Autowired
	private UserSkillService userSkillService;
	
	@Autowired
	private UserRepository userRepo;
	
	@PostMapping("/add/userskill/{userId}")
	public ResponseEntity<GenericResponse> addUserSkills(@PathVariable Long userId,
			           @RequestBody SkillReqSelection skillReqSel){
    User user = userRepo.findById(userId)
    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    
    return ResponseEntity.ok(userSkillService.addUserSkills(skillReqSel, null, user));
}
}

