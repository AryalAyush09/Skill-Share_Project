package com.project.skill_share.DTO;

public class ChatHistoryRequestDto {
	    private Long receiverId;
	    private int page;
	    private int size;
	    
		public Long getReceiverId() {
			return receiverId;
		}
		public void setReceiverId(Long receiverId) {
			this.receiverId = receiverId;
		}
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		public int getSize() {
			return size;
		}
		public void setSize(int size) {
			this.size = size;
		}    
}
