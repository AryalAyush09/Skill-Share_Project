package com.project.skill_share.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.skill_share.DTO.ChatMessageDto;
import com.project.skill_share.response.ApiResponse;
import com.project.skill_share.services.MessageService;

@RestController
@RequestMapping("/api/messages")

public class MessageController {
	private final MessageService msgService;
	
	public MessageController(MessageService msgService) {
		this.msgService = msgService;
	}
	
	  @GetMapping("/chat")
	    public ResponseEntity<ApiResponse<?>> getChat(Authentication auth, @RequestParam Long receiverId,
	        @RequestParam int page, @RequestParam int size) {
            Long senderId = Long.parseLong(auth.getName());
	        ApiResponse<?> response = msgService.getChatBetweenUsers(senderId, receiverId, page, size);
	        return ResponseEntity.ok(response);
	    }
	  
	  @PostMapping("/mark-seen")
	  public ResponseEntity<?> markMessageAsSeen(@RequestParam Long senderId, Authentication auth){
	      Long receiverId = Long.parseLong(auth.getName());  
	      msgService.markMessagesAsSeen(senderId, receiverId);
	      return ResponseEntity.ok().build();
	  }

	  @GetMapping("/unread-count")
	   public ResponseEntity<?> getUnreadCount(Authentication auth){
		  Long senderId = Long.parseLong(auth.getName());
		  return ResponseEntity.ok(msgService.countUnreadMessages(senderId));
	  }
	  @PostMapping("/chat/send")
	  public ResponseEntity<?> sendMessage(@RequestBody ChatMessageDto dto, Authentication auth) {
	      Long senderId = Long.parseLong(auth.getName());
	      dto.setSenderId(senderId);

	      ChatMessageDto saved = msgService.saveMessage(dto);
	      return ResponseEntity.ok(new ApiResponse<>(true, "Message sent", saved));
	  }

}