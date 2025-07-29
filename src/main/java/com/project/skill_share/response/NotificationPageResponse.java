package com.project.skill_share.response;

import java.util.List;

import com.project.skill_share.DTO.NotificationResponseDto;

public class NotificationPageResponse {
	
	    private List<NotificationResponseDto> notifications;
	    private int currentPage;
	    private int totalPages;
	    private long totalItems;

	    public NotificationPageResponse(List<NotificationResponseDto> notifications,
	                                     int currentPage,
	                                     int totalPages,
	                                     long totalItems) {
	        this.notifications = notifications;
	        this.currentPage = currentPage;
	        this.totalPages = totalPages;
	        this.totalItems = totalItems;
	    }

	    public List<NotificationResponseDto> getNotifications() {
	        return notifications;
	    }

	    public void setNotifications(List<NotificationResponseDto> notifications) {
	        this.notifications = notifications;
	    }

	    public int getCurrentPage() {
	        return currentPage;
	    }

	    public void setCurrentPage(int currentPage) {
	        this.currentPage = currentPage;
	    }

	    public int getTotalPages() {
	        return totalPages;
	    }

	    public void setTotalPages(int totalPages) {
	        this.totalPages = totalPages;
	    }

	    public long getTotalItems() {
	        return totalItems;
	    }

	    public void setTotalItems(long totalItems) {
	        this.totalItems = totalItems;
	    }
	}

