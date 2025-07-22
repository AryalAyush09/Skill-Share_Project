package com.project.skill_share.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import com.project.skill_share.DTO.ChatMessageDto;

@Controller
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    
    public ChatWebSocketController (SimpMessagingTemplate messagingTemplate) {
    	this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(ChatMessageDto message, Authentication auth) {
    	Long senderId = Long.parseLong(auth.getName());
    	message.setSenderId(senderId);
    	
    	if(message.getReceiverId() == null || message.getContent() == null) {
    		return;
    	}
        messagingTemplate.convertAndSend("/topic/messages/" + message.getReceiverId(), message);
    }

    @MessageMapping("/chat.typing")
    public void typing(ChatMessageDto message, Authentication auth) {
    	Long senderId = Long.parseLong(auth.getName());
        message.setSenderId(senderId);

        if (message.getReceiverId() == null) {
            return;
        }
        messagingTemplate.convertAndSend("/topic/typing/" + message.getReceiverId(), senderId);
    }
}

