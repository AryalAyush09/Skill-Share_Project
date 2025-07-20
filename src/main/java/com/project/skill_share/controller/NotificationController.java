package com.project.skill_share.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.skill_share.DTO.MatchResponseDto;
import com.project.skill_share.enums.MatchStatus;
import com.project.skill_share.response.ApiResponse;
import com.project.skill_share.services.MatchService;
import com.project.skill_share.services.NotificationService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class NotificationController {
	private final NotificationService notificationService;
	private final MatchService matchService;
	
	public NotificationController(NotificationService notificationService, MatchService matchService) {
	    this.notificationService = notificationService;	
	    this.matchService = matchService;
	}
	
	@Operation(summary = "Notification")
	@GetMapping("/get/notifications")
	public ApiResponse<?> getNotifications(Authentication auth,@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {
      Long userId = Long.parseLong(auth.getName());
	    return notificationService.getUserNotification(userId, page, size);
	}
	
	@Operation(summary = "Send a notification to a user")
	@PostMapping("send/notifications")
	public ApiResponse<?> sendNotification(Authentication auth, @RequestParam String message) {
		Long userId = Long.parseLong(auth.getName());
	    return notificationService.sendNotification(userId, message);
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
