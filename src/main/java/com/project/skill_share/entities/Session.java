package com.project.skill_share.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.skill_share.enums.MatchStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "session")
public class Session {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private Long user1Id;
  private Long user2Id;
  
  
  @OneToOne
  @JoinColumn(name = "match_id")
  private MatchUser match;
  
  @CreationTimestamp
  private LocalDateTime startedAt;
  
  private LocalDateTime endedAt;
  
  @Enumerated(EnumType.STRING)
  private MatchStatus type;

  
  private Long durationInMinutes;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public Long getUser1Id() {
	return user1Id;
}

public void setUser1Id(Long user1Id) {
	this.user1Id = user1Id;
}

public Long getUser2Id() {
	return user2Id;
}

public void setUser2Id(Long user2Id) {
	this.user2Id = user2Id;
}

public MatchUser getMatch() {
	return match;
}

public void setMatch(MatchUser match) {
	this.match = match;
}

public LocalDateTime getStartedAt() {
	return startedAt;
}

public void setStartedAt(LocalDateTime startedAt) {
	this.startedAt = startedAt;
}

public LocalDateTime getEndedAt() {
	return endedAt;
}

public void setEndedAt(LocalDateTime endedAt) {
	this.endedAt = endedAt;
}

public MatchStatus getType() {
	return type;
}

public void setType(MatchStatus type) {
	this.type = type;
}

public Long getDurationInMinutes() {
	return durationInMinutes;
}

public void setDurationInMinutes(Long durationInMinutes) {
	this.durationInMinutes = durationInMinutes;
}
}
