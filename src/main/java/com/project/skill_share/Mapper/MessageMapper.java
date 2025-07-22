package com.project.skill_share.Mapper;

import org.springframework.stereotype.Component;

import com.project.skill_share.DTO.ChatMessageDto;
import com.project.skill_share.DTO.MatchedUserDto;
import com.project.skill_share.entities.MessageBox;
import com.project.skill_share.entities.User;

@Component
public class MessageMapper {
	
   public static ChatMessageDto toDto(MessageBox entity) {
	   ChatMessageDto dto = new ChatMessageDto();
	    dto.setId(entity.getId());
	    dto.setSenderId(entity.getSenderId());
	    dto.setReceiverId(entity.getReceiverId());
	    dto.setMsgType(entity.getMsgType());
	    dto.setContent(entity.getContent());
	    dto.setSeen(entity.isSeen());
	    dto.setTimeStamp(entity.getCreatedAt());
	    return dto;
   }
   
   public static MessageBox toEntity(ChatMessageDto dto) {
	   MessageBox entity = new MessageBox();
	   entity.setId(dto.getId());
	   entity.setSenderId(dto.getSenderId());
	   entity.setReceiverId(dto.getReceiverId());
	   entity.setMsgType(dto.getMsgType());
	   entity.setContent(dto.getContent());
	   entity.setSeen(dto.isSeen());
	   entity.setCreatedAt(dto.getTimeStamp());
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
