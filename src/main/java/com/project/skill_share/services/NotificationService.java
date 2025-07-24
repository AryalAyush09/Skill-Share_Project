package com.project.skill_share.services;

import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;

import com.project.skill_share.GlobalErrorHandler.UserNotFoundException;
import com.project.skill_share.entities.Notification;
import com.project.skill_share.entities.User;
import com.project.skill_share.repository.NotificationRepository;
import com.project.skill_share.repository.UserRepository;
import com.project.skill_share.response.ApiResponse;
import com.project.skill_share.response.PaginatedNotificationResponse;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class NotificationService {
     private final NotificationRepository notificationRepo;
     private final UserRepository userRepo;
     private final SimpMessagingTemplate messagingTemplate;
     
     public NotificationService(NotificationRepository notificationRepo, 
    		 SimpMessagingTemplate messagingTemplate, UserRepository userRepo) {
    	 this.notificationRepo = notificationRepo;
    	 this.userRepo = userRepo;
    	 this.messagingTemplate = messagingTemplate;
     }
     
     public ApiResponse<?> sendNotification(Long senderId, String message, Long receiverId){
    	   System.out.println("Inside sendNotification: userId=" + senderId + ", message=" + message);
    	 getUserById(senderId);
    	 getUserById(receiverId);
    	 
    	 if(message == null || message.isBlank()) {
    		 return new ApiResponse<>(false, "Notification messgae cannot be null", null);
    	 } 
    	 System.out.println("Creating notification for userId = " + senderId);
    	 Notification not = new Notification();
    	 not.setUserId(receiverId);
    	 not.setSenderId(senderId);
    	 not.setMessage(message);
    	 not.setRead(false);
    	 
    	  notificationRepo.saveAndFlush(not);
    	  messagingTemplate.convertAndSend("/topic/notifications/" + receiverId, message);
    	  
     return new ApiResponse<>(true, "Notification sent Successfully", not);
   }
     
     public ApiResponse<?> getUserNotification(Long userId, int page, int size){
    	 
    	 getUserById(userId);
    	  Pageable pageable = PageRequest.of(page, size);
    	    Page<Notification> notificationPage = notificationRepo.findByUserIdOrderByCreatedAtDesc(userId, pageable);

    	    List<Notification> notifications = notificationPage.getContent();
    	
    	 boolean hasUnread = false;
    	 for(Notification notification : notifications) {
    		 if(!notification.isRead()) {
    			 notification.setRead(true);
    			 hasUnread = true;
    		 }
    	 }
    	 if(hasUnread) {
    		 notificationRepo.saveAll(notifications);
    	 }
    	 
    	 PaginatedNotificationResponse response = new PaginatedNotificationResponse(
 	            notifications, notificationPage.getNumber(), notificationPage.getTotalPages(),
 	            notificationPage.getTotalElements());
    	 
    	 return new ApiResponse<>(true ,"Fetched Successfully", response);
     }
     
 	private User getUserById(Long currentUserId) {
		return userRepo.findById(currentUserId).orElseThrow(() -> new UserNotFoundException("User not Found"));
	}
}
