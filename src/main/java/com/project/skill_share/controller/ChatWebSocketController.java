package com.project.skill_share.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import com.project.skill_share.DTO.ChatHistoryRequestDto;
import com.project.skill_share.DTO.ChatMessageCreateDto;
import com.project.skill_share.DTO.ChatMessageDto;
import com.project.skill_share.DTO.ChatSeenDto;
import com.project.skill_share.response.ApiResponse;
import com.project.skill_share.services.MessageService;

@Controller
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService msgService;
    
    
    public ChatWebSocketController(SimpMessagingTemplate messagingTemplate, MessageService msgService) {
        this.messagingTemplate = messagingTemplate;
        this.msgService = msgService;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageCreateDto message, Authentication auth) {
        Long senderId = Long.parseLong(auth.getName());

        ChatMessageDto saved = msgService.sendMessage(senderId, message);
        messagingTemplate.convertAndSend("/topic/messages/" + message.getReceiverId(), saved);
    }

    @MessageMapping("/chat.typing")
    public void typing(ChatMessageDto message, Authentication auth) {
        Long senderId = Long.parseLong(auth.getName());

        messagingTemplate.convertAndSend("/topic/typing/" + message.getReceiverId(), senderId);
    }
    
    @MessageMapping("/chat.seen")
     public void markSeen(ChatSeenDto dto, Authentication auth) {
    	Long recieverId = Long.parseLong(auth.getName());
         msgService.markMessagesSeen(dto.getSenderId(), recieverId);
    }
    
    @MessageMapping("/chat.history")
    @SendToUser("/queue/chat-history") // Private response
    public ApiResponse<?> getChatHistory(ChatHistoryRequestDto dto, Authentication auth) {
        Long currentUserId = Long.parseLong(auth.getName());
        return msgService.getChatBetweenUsers(currentUserId, dto.getReceiverId(), dto.getPage(), dto.getSize());
    }

    @MessageMapping("/chat.unread-count")
    @SendToUser("/queue/unread-count")
    public Long getUnreadCount(Authentication auth) {
        Long userId = Long.parseLong(auth.getName());
        return msgService.countUnreadMessages(userId);
    }
}


