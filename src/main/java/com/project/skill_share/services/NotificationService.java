package com.project.skill_share.services;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;

import com.project.skill_share.DTO.NotificationRequestDto;
import com.project.skill_share.DTO.NotificationResponseDto;
import com.project.skill_share.GlobalErrorHandler.UserNotFoundException;
import com.project.skill_share.entities.Notification;
import com.project.skill_share.entities.User;
import com.project.skill_share.enums.MatchStatus;
import com.project.skill_share.repository.NotificationRepository;
import com.project.skill_share.repository.UserRepository;
import com.project.skill_share.response.ApiResponse;
import com.project.skill_share.response.NotificationPageResponse;
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
     
     public ApiResponse<?> sendNotification(Long senderId, NotificationRequestDto dto) {

    	    Long receiverId = dto.getReceiverUserId();

    	    if (dto.getMessage() == null || dto.getMessage().isBlank()) {
    	        return new ApiResponse<>(false, "Notification message cannot be null", null);
    	    }

    	    System.out.println("Creating notification for receiverId = " + receiverId);
    	    System.out.println("Sending notification to userId = " + receiverId + ", from senderId = " + senderId);

    	    Notification not = new Notification();
    	    not.setUserId(receiverId);
    	    not.setSenderId(senderId);
    	    not.setMessage(dto.getMessage());
    	    not.setStatus(MatchStatus.PENDING);
    	    not.setCreatedAt(LocalDateTime.now());

    	    notificationRepo.save(not);

    	    NotificationResponseDto response = new NotificationResponseDto(
    	        not.getId(), receiverId, dto.getMessage(), senderId, not.getCreatedAt(),not.getStatus());
    	    
    	    System.out.println("Sending notification:");
    	    System.out.println("SenderId: " + senderId);
    	    System.out.println("ReceiverId: " + receiverId);
    	    System.out.println("Message: " + dto.getMessage());

    	    
    	    messagingTemplate.convertAndSend("/topic/notifications" + receiverId, response);
    	    return new ApiResponse<>(true, "Notification sent Successfully", not);
    	}

     public void markAsSeen(Long userId, Long notificationId) {
    	    Notification not = notificationRepo.findById(notificationId).orElse(null);
    	    if (not != null && not.getUserId().equals(userId)) {
    	        not.setSeen(true);
    	        notificationRepo.save(not);
    	    }
    	}

     public ApiResponse<?> getUserNotification(Long userId, int page, int size) {
    	    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
    	    Page<Notification> notificationPage = notificationRepo.findByUserId(userId, pageable);

    	    List<NotificationResponseDto> dtos = notificationPage.getContent().stream()
    	            .map(not -> new NotificationResponseDto(
    	                    not.getId(),not.getUserId(),
    	                    not.getMessage(),not.getSenderId(),
    	                    not.getCreatedAt(),not.getStatus()))
    	            .collect(Collectors.toList());

    	    NotificationPageResponse response = new NotificationPageResponse(dtos,notificationPage.getNumber(),
    	            notificationPage.getTotalPages(), notificationPage.getTotalElements());
    	    System.out.println("Fetching notifications for userId: " + userId);

    	    return new ApiResponse<>(true, "Fetched Successfully", response);
    	}

 	private User getUserById(Long currentUserId) {
		return userRepo.findById(currentUserId).orElseThrow(() -> new UserNotFoundException("User not Found"));
	}


}
