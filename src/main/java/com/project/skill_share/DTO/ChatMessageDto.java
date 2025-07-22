package com.project.skill_share.DTO;

import java.time.LocalDateTime;

import com.project.skill_share.enums.MessageType;

public class ChatMessageDto {
   private Long id;
   
   private Long senderId;
  
   private Long receiverId;
   
   private String content;
   
   private MessageType msgType;
   
   private boolean seen;
   
   private LocalDateTime timeStamp;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Long getSenderId() {
	return senderId;
}

public void setSenderId(Long senderId) {
	this.senderId = senderId;
}

public void setReceiverId(Long long1) {
	this.receiverId = long1;
}

public Long getReceiverId() {
	return receiverId;
}

public String getContent() {
	return content;
}

public void setContent(String content) {
	this.content = content;
}

public MessageType getMsgType() {
	return msgType;
}

public void setMsgType(MessageType msgType) {
	this.msgType = msgType;
}

public boolean isSeen() {
	return seen;
}

public void setSeen(boolean seen) {
	this.seen = seen;
}

public LocalDateTime getTimeStamp() {
	return timeStamp;
}

public void setTimeStamp(LocalDateTime timeStamp) {
	this.timeStamp = timeStamp;
}
 
}
