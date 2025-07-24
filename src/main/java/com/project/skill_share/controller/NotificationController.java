package com.project.skill_share.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.skill_share.DTO.NotificationRequestDto;
import com.project.skill_share.response.ApiResponse;
import com.project.skill_share.services.NotificationService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class NotificationController {
	private final NotificationService notificationService;
	
	public NotificationController(NotificationService notificationService) {
	    this.notificationService = notificationService;
	}
	
	@Operation(summary = "Notification")
	@GetMapping("/get/notifications")
	public ApiResponse<?> getNotifications(Authentication auth,@RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {
      Long userId = Long.parseLong(auth.getName());
	    return notificationService.getUserNotification(userId, page, size);
	}
	
	@Operation(summary = "Manually Send a notification to a user")
	@PostMapping("send/notifications")
	public ApiResponse<?> sendNotification(Authentication auth, @RequestBody NotificationRequestDto dto) {
		Long senderId = Long.parseLong(auth.getName());
	    return notificationService.sendNotification(senderId, dto.getMessage(), dto.getReceiverUserId());
	}
}
