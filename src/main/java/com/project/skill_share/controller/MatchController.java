package com.project.skill_share.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.skill_share.DTO.MatchRequestDto;
import com.project.skill_share.DTO.MatchResponseDto;
import com.project.skill_share.enums.MatchStatus;
import com.project.skill_share.response.ApiResponse;
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
   
   @Operation(summary = "Send Match Request")
   @PostMapping("/send/request")
    public ResponseEntity<?> sendRequest(Authentication auth, @RequestBody MatchRequestDto dto){
      Long userId = Long.parseLong(auth.getName());
      ApiResponse<?> response = matchService.sendRequest(userId, dto);
      return ResponseEntity.ok(response);
   }
   
   @Operation(summary = "Get match request detail")
   @GetMapping("/request/detail")
    public ResponseEntity<?> getMatchedUserDetail(@RequestParam Long matchId, Authentication auth){
	   Long curretUserId = Long.parseLong(auth.getName());
	   ApiResponse<?> response = matchService.getRequestDetail(matchId, curretUserId);
	  return ResponseEntity.ok(response);
   }
   
   @Operation(summary = "Respond to match request")
	@PostMapping("/match/respond")
	public ResponseEntity<?> respondToMatchRequest(Authentication auth, @RequestBody MatchResponseDto dto) {
	    Long currentUserId = Long.parseLong(auth.getName());

	    MatchStatus responseStatus;
	    try {
	        responseStatus = MatchStatus.valueOf(dto.getResponse().toUpperCase());
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Invalid response status", null));
	    }

	    ApiResponse<?> response = matchService.respondToRequest(currentUserId, dto.getRequesterUserId(), responseStatus);
	    return ResponseEntity.ok(response);
	}
}
