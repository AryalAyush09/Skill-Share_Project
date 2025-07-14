package com.project.skill_share.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.skill_share.services.MatchService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/users")
public class MatchController {
	
   private final MatchService matchService;
   
   public MatchController(MatchService matchService) {
	   this.matchService = matchService;
   }
   
   @Operation(summary = "Match the Users Skill")
   @PostMapping("/match")
	   public ResponseEntity<?> matchUser(Authentication auth){
		   Long userId  = Long.parseLong(auth.getName());
		   return ResponseEntity.ok(matchService.matchingUser(userId));
	   }
}
