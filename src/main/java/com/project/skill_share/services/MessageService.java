package com.project.skill_share.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.project.skill_share.DTO.ChatMessageDto;
import com.project.skill_share.DTO.ChatResponseDto;
import com.project.skill_share.DTO.MatchedUserDto;
import com.project.skill_share.Mapper.MessageMapper;
import com.project.skill_share.entities.MessageBox;
import com.project.skill_share.entities.User;
import com.project.skill_share.repository.MessageBoxRepository;
import com.project.skill_share.repository.UserRepository;
import com.project.skill_share.response.ApiResponse;

import jakarta.transaction.Transactional;

@Service
public class MessageService {
  
	private final MessageBoxRepository messageRepo;
	private final UserRepository userRepo;
	private final SimpMessagingTemplate messagingTemplate;
	
	public MessageService (MessageBoxRepository messageRepo, UserRepository userRepo, SimpMessagingTemplate messagingTemplate ) {
		this.messageRepo = messageRepo;
		this.userRepo = userRepo;
		this.messagingTemplate = messagingTemplate;
	}
	
	public ApiResponse<?> getChatBetweenUsers(Long senderId, Long viewerId, int page, int size){
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		
		Page<MessageBox> messagePage = messageRepo.findBySenderIdAndReceiverIdOrReceiverIdAndSenderId(senderId, viewerId, 
				senderId, viewerId, pageable);
		
	    List<MessageBox> messages = messagePage.getContent();
	    
		// convert messgae to dto
		List<ChatMessageDto> messageDtos= messages.stream().map(MessageMapper :: toDto)
				.collect(Collectors.toList());
		
		// uniqueids
		Set<Long> userIds = messages.stream()
			    .flatMap(m -> Stream.of(m.getSenderId(), m.getReceiverId()))
			    .collect(Collectors.toSet());
				
		List<User> users = userRepo.findAllById(userIds);
		
		List<MatchedUserDto> userDtos = users.stream().map(MessageMapper :: touserDto)
				.collect(Collectors.toList());
		
		ChatResponseDto dto = new ChatResponseDto();
		  dto.setMessages(messageDtos);
		  dto.setUsers(userDtos);
		  dto.setCurrentPages(page);
		  dto.setTotalPages(messagePage.getTotalPages());
		  
		  return new ApiResponse<>(true, "Success", dto);
	}
	
	public long countUnreadMessages(Long userId) {
	    return messageRepo.countByReceiverIdAndIsSeenFalse(userId);
	}
	
	@Transactional
	public void markMessagesAsSeen(Long senderId, Long receiverId) {
	    messageRepo.markMessagesAsSeen(senderId, receiverId);
	}
	
	@Transactional
	public ChatMessageDto saveMessage(ChatMessageDto dto) {
	    MessageBox entity = MessageMapper.toEntity(dto);
	    messageRepo.save(entity);

	    ChatMessageDto messageDto = MessageMapper.toDto(entity);

	    messagingTemplate.convertAndSend("/topic/messages/" + entity.getReceiverId(), messageDto);

	    return messageDto;
	}
}
