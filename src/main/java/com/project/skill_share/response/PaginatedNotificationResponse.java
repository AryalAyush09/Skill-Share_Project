package com.project.skill_share.response;

import com.project.skill_share.entities.Notification;
import java.util.List;

public class PaginatedNotificationResponse {
    private List<Notification> notifications;
    private int currentPage;
    private int totalPages;
    private long totalItems;

    public PaginatedNotificationResponse(List<Notification> notifications, int currentPage, int totalPages, long totalItems) {
        this.notifications = notifications;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
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
