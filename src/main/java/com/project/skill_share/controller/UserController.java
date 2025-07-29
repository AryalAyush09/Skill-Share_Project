package com.project.skill_share.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.skill_share.DTO.SocialLinkDto;
import com.project.skill_share.DTO.UserSkillResponseDTO;
import com.project.skill_share.DTO.UserUpdateDto;
import com.project.skill_share.response.ApiResponse;
import com.project.skill_share.services.CategoryService;
import com.project.skill_share.services.SkillService;
import com.project.skill_share.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")

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
    
    @Operation(summary = "Add new social links (e.g., GitHub, Facebook, etc.)")
    @PostMapping("/social-links")
     public ResponseEntity<?> postSocialLinks(Authentication auth, @RequestBody List<SocialLinkDto> linksDto){
    	Long userId = Long.parseLong(auth.getName());
    	userService.addOrUpdateSocialLinks(userId, linksDto);
    	return ResponseEntity.ok(new ApiResponse<>(true, "Socaillinks add", null));
    	
    }
    
    @PutMapping("/update")
     public ResponseEntity<ApiResponse<UserUpdateDto>> updateUser(Authentication auth,@Valid @RequestBody UserUpdateDto dto){
    	Long userId = Long.parseLong(auth.getName());
    	ApiResponse<UserUpdateDto> response = userService.updateUserProfile(userId, dto);
    	return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/social-links/{platform}")
    public ResponseEntity<?> deleteSocialLinkByPlatform(Authentication auth, @PathVariable String platform) {
        Long userId = Long.parseLong(auth.getName());
        userService.deleteSocialLink(userId, platform);
        return ResponseEntity.ok(new ApiResponse<>(true, "Social link deleted", null));
    }
    
    @Operation(summary = "Get current user's social links")
    @GetMapping("/social-links")
    public ResponseEntity<ApiResponse<List<SocialLinkDto>>> getSocialLinks(Authentication auth) {
        Long userId = Long.parseLong(auth.getName());
        List<SocialLinkDto> links = userService.getSocialLinks(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched social links", links));
    }

}