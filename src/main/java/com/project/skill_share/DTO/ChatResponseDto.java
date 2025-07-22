package com.project.skill_share.DTO;

import java.util.List;

public class ChatResponseDto {
  private List<MatchedUserDto> users;
  private List<ChatMessageDto> messages;
  private int currentPages;
  private int totalPages;
  
public List<MatchedUserDto> getUsers() {
	return users;
}
public void setUsers(List<MatchedUserDto> users) {
	this.users = users;
}

public List<ChatMessageDto> getMessages() {
	return messages;
}

public void setMessages(List<ChatMessageDto> messages) {
	this.messages = messages;
}

public int getCurrentPages() {
	return currentPages;
}
public void setCurrentPages(int currentPages) {
	this.currentPages = currentPages;
}

public int getTotalPages() {
	return totalPages;
}

public void setTotalPages(int totalPages) {
	this.totalPages = totalPages;
} 

}
