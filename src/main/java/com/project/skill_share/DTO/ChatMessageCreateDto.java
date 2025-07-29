package com.project.skill_share.DTO;

import com.project.skill_share.enums.MessageType;

public class ChatMessageCreateDto {
    private Long receiverId;
    private String content;
    private MessageType msgType;
    
	public ChatMessageCreateDto() {
	}
	public Long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
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
    
    
}

