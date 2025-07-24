package com.project.skill_share.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.skill_share.DTO.StartSessionRequest;
import com.project.skill_share.response.ApiResponse;
import com.project.skill_share.services.SessionService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessionService;
    
    public SessionController(SessionService sessionService) {
    	this.sessionService = sessionService;
    }
    
    @Operation(summary = "Starting the session")
    @PostMapping("/start")
    public ResponseEntity<ApiResponse<?>> startSession(@RequestBody StartSessionRequest dto) {
       return ResponseEntity.ok(sessionService.startSession(dto.getUser1Id(),
    		   dto.getUser2Id(), dto.getMatchId()));
    }

    @Operation(summary = "End the session")
    @PostMapping("/{id}/end")
    public ResponseEntity<ApiResponse<?>>  endSession(@PathVariable Long id) {
    	   return ResponseEntity.ok(sessionService.endSession(id));
    }
}
