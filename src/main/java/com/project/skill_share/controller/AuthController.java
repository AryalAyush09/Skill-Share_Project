package com.project.skill_share.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.skill_share.DTO.UserRegisterRequestDto;
import com.project.skill_share.entities.LoginForm;
import com.project.skill_share.response.ApiResponse;
import com.project.skill_share.services.AuthService;

@RestController
@RequestMapping("/api")


public class AuthController {

    private final AuthService authService;
    
    public AuthController(AuthService authService) {
    	this.authService = authService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterRequestDto request) {
        authService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> loginUser(@RequestBody LoginForm loginForm) {
        return ResponseEntity.ok(authService.loginUser(loginForm));
    }
}
