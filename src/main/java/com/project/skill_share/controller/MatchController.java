package com.project.skill_share.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.skill_share.DTO.MatchRequestDto;
import com.project.skill_share.response.ApiResponse;
import com.project.skill_share.services.MatchService;
import com.project.skill_share.services.MatchUserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/users")
public class MatchController {
	
   private final MatchUserService matchUserService;
   private final MatchService matchService;
   
   public MatchController(MatchUserService matchUserService, MatchService matchService) {
	   this.matchUserService = matchUserService;
	   this.matchService = matchService;
   }
   
   @Operation(summary = "Match the Users Skill")
   @PostMapping("/match")
	   public ResponseEntity<?> matchUser(Authentication auth){
		   Long userId  = Long.parseLong(auth.getName());
		   return ResponseEntity.ok(matchService.matchingUser(userId));
	   }
   
   @Operation(summary = "Match Request")
   @PostMapping("/send/request")
    public ResponseEntity<?> sendRequest(Authentication auth, @RequestBody MatchRequestDto dto){
      Long userId = Long.parseLong(auth.getName());
      ApiResponse<?> response = matchService.sendRequest(userId, dto);
      return ResponseEntity.ok(response);
   }
}
