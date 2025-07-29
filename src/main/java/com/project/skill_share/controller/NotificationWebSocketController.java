package com.project.skill_share.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import com.project.skill_share.DTO.NotificationPageRequestDto;
import com.project.skill_share.DTO.NotificationRequestDto;
import com.project.skill_share.response.ApiResponse;
import com.project.skill_share.services.NotificationService;

@Controller
public class NotificationWebSocketController {
      private NotificationService notificationService;
      
     public NotificationWebSocketController(NotificationService notificationService) {
    	 this.notificationService = notificationService;
     }
      
      @MessageMapping("/notifications.send")
      public void sendNotification(Authentication auth, @Payload NotificationRequestDto dto){
          Long senderId = Long.parseLong(auth.getName());
          notificationService.sendNotification(senderId, dto);
      }
      	
      @MessageMapping("/notifications.markSeen")
      public void markSeen(@Payload Long notificationId, Authentication auth) {
          Long userId = Long.parseLong(auth.getName());
          notificationService.markAsSeen(userId, notificationId);
      }
      
      @MessageMapping("/notifications.get")
      @SendToUser("/queue/notifications")
      public ApiResponse<?> getNotifications(Authentication auth, @Payload NotificationPageRequestDto request) {
          Long userId = Long.parseLong(auth.getName());
          return notificationService.getUserNotification(userId, request.getPage(), request.getSize());
      }
}
