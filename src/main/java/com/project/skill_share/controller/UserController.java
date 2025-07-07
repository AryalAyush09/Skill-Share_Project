package com.project.skill_share.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.skill_share.DTO.UserSkillResponseDTO;
import com.project.skill_share.DTO.UserUpdateDto;
import com.project.skill_share.response.ApiResponse;
import com.project.skill_share.services.CategoryService;
import com.project.skill_share.services.SkillService;
import com.project.skill_share.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final SkillService skillService;
    private final CategoryService categoryService;

    public UserController(UserService userService, SkillService skillService, CategoryService categoryService) {
        this.userService = userService;
        this.skillService = skillService;
        this.categoryService = categoryService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> getUserById(Authentication auth) {
        Long userId = Long.parseLong(auth.getName());
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/all/categories")
    public ResponseEntity<?> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/all/skills")
    public ResponseEntity<?> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkill());
    }
     
    @GetMapping("/my-skills")
     public ResponseEntity<ApiResponse<UserSkillResponseDTO>> getUserSkills(Authentication auth){
    	Long userId = Long.parseLong(auth.getName());
    	ApiResponse<UserSkillResponseDTO> response = userService.getUserSkill(userId);
    	return ResponseEntity.ok(response);
    }
    
    @PutMapping("/update")
     public ResponseEntity<ApiResponse<UserUpdateDto>> updateUser(Authentication auth,@Valid @RequestBody UserUpdateDto dto){
    	Long userId = Long.parseLong(auth.getName());
    	ApiResponse<UserUpdateDto> response = userService.updateUser(userId, dto);
    	return ResponseEntity.ok(response);
    }
}