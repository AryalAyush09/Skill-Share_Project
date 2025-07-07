package com.project.skill_share.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.skill_share.DTO.SkillReqDTO;
import com.project.skill_share.entities.Category;
import com.project.skill_share.response.ApiResponse;
import com.project.skill_share.response.GenericResponse;
import com.project.skill_share.services.CategoryService;
import com.project.skill_share.services.SkillService;
import com.project.skill_share.services.UserService;

@RestController
@RequestMapping("/api/admin")
	
	public class AdminController {
	    
	    private final CategoryService categoryService;
	    private final SkillService skillService;
	    private final UserService userService;
	    
	    public AdminController(CategoryService categoryService, SkillService skillService,
	    		   UserService userService) {
	        this.categoryService = categoryService;
	        this.skillService = skillService;
	        this.userService = userService;
	    }
	    
	    @PostMapping("/add/categories")
	    public ResponseEntity<GenericResponse> addCategory(@RequestBody Category category) {
	        return ResponseEntity.ok(categoryService.registerCat(category));
	    }
	    
	    @PostMapping("/add/skills")
	    public ResponseEntity<GenericResponse> addSkill(@RequestBody SkillReqDTO skillDto) {
	        return ResponseEntity.ok(skillService.addSkill(skillDto));
	    }
	    
	    @GetMapping("/get/users")
	    public ResponseEntity<ApiResponse<?>> getAllUsers(){
	    	return ResponseEntity.ok(userService.getAllUser());
	    }
	}

