package com.project.skill_share.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
	    name = "ratings",
	    uniqueConstraints = 
	{@UniqueConstraint(columnNames = {"session_id", "rater_user_id"}) }
	    )


public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "session_id")
	private Session session;
	
	@Column(name = "rater_user_id")
	private Long raterUserId;

	@Column(name = "ratee_user_id")
	private Long rateeUserId;

	private int stars;
	
	@Column(columnDefinition = "TEXT")  
	private String feedback;
	
	@CreationTimestamp
	private LocalDateTime createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}


	public Long getRaterUserId() {
		return raterUserId;
	}

	public void setRaterUserId(Long raterUserId) {
		this.raterUserId = raterUserId;
	}

	public Long getRateeUserId() {
		return rateeUserId;
	}

	public void setRateeUserId(Long rateeUserId) {
		this.rateeUserId = rateeUserId;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
