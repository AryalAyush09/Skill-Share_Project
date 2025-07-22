package com.project.skill_share.entities;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

    @Entity
	@Table(name = "user_cvs")
	 public class UserCV {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String cvUrl;       
	    private String publicId;        

	    @CreationTimestamp
	    private LocalDateTime uploadedAt;

	    @OneToOne
	    @JoinColumn(name = "user_id", nullable = false)
	    private User user;

	   
	    public Long getId() { 
	    	return id; 
	    	}
	    public void setId(Long id) {
	    	this.id = id; 
	    	}

	    public String getCvUrl() {
	    	return cvUrl;
	    	}
	    public void setCvUrl(String cvUrl) { 
	    	this.cvUrl = cvUrl;
	    	}

	    public String getPublicId() {
	    	return publicId;
	    	}
	    
	    public void setPublicId(String publicId) {
	    	this.publicId = publicId; 
	    	}

	    public LocalDateTime getUploadedAt() {
	    	return uploadedAt; 
	    	}
	    public void setUploadedAt(LocalDateTime uploadedAt) {
	    	this.uploadedAt = uploadedAt;
	    	}

	    public User getUser() {
	    	return user; 
	    	}
	    public void setUser(User user) {
	    	this.user = user; 
	    	}
	}

