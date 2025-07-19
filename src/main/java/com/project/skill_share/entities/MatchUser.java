package com.project.skill_share.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.skill_share.enums.MatchStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "match_user")
public class MatchUser {
	
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   
   private Long currentUserId;
   private Long otherUserId;
   private float matchingScore;
   
   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private MatchStatus matchStatus;
   
   @CreationTimestamp
   private LocalDateTime currentAt;
   
   public MatchUser() {
   }

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Long getCurrentUserId() {
	return currentUserId;
}

public void setCurrentUserId(Long currentUserId) {
	this.currentUserId = currentUserId;
}

public Long getOtherUserId() {
	return otherUserId;
}

public void setOtherUserId(Long otherUserId) {
	this.otherUserId = otherUserId;
}


public float getMatchingScore() {
	return matchingScore;
}

public void setMatchingScore(float matchingScore) {
	this.matchingScore = matchingScore;
}

public LocalDateTime getCurrentAt() {
	return currentAt;
}

public void setCurrentAt(LocalDateTime currentAt) {
	this.currentAt = currentAt;
}

public MatchStatus getMatchStatus() {
	return matchStatus;
}

public void setMatchStatus(MatchStatus matchStatus) {
	this.matchStatus = matchStatus;
}

}
