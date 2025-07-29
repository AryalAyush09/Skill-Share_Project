package com.project.skill_share.Mapper;

import org.springframework.stereotype.Component;

import com.project.skill_share.DTO.ChatMessageDto;
import com.project.skill_share.DTO.MatchedUserDto;
import com.project.skill_share.entities.MessageBox;
import com.project.skill_share.entities.User;

@Component
public class MessageMapper {
	
	public static ChatMessageDto toDto(MessageBox box) {
	    ChatMessageDto dto = new ChatMessageDto();
	    dto.setId(box.getId());
	    dto.setSenderId(box.getSender().getId());
	    dto.setReceiverId(box.getReceiver().getId());
	    dto.setContent(box.getContent());
	    dto.setMsgType(box.getMsgType());
	    dto.setSeen(box.isSeen());
	    dto.setTimeStamp(box.getCreatedAt());
	    return dto;
	}

   public static MessageBox toEntity(ChatMessageDto dto) {
	    MessageBox entity = new MessageBox();
	    User sender = new User();
	    sender.setId(dto.getSenderId());
	    entity.setSender(sender);

	    User receiver = new User();
	    receiver.setId(dto.getReceiverId());
	    entity.setReceiver(receiver);

	    entity.setMsgType(dto.getMsgType());
	    entity.setContent(dto.getContent());
	    return entity;
	}
   
   
   public static MatchedUserDto touserDto(User user) {
	   MatchedUserDto dto = new MatchedUserDto();
	   dto.setId(user.getId());
	   dto.setUserName(user.getUsername());
	   dto.setProfileImageUrl(user.getProfileImage() != null
			   ? user.getProfileImage().getImageUrl() : null);
	   return dto;
   }
}
