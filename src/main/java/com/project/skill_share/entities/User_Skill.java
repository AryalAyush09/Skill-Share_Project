package com.project.skill_share.entities;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import com.project.skill_share.enums.SkillType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "user_skill_table",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "skill_id", "type"})
    })

public class User_Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
  
    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
	 private Skill skillName;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userName;

    @Enumerated(EnumType.STRING)
   	private SkillType type;
    
	@CreationTimestamp
    private LocalDateTime createdAt;

	 public User_Skill() {	 
	 }
	 
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Skill getSkillName() {
		return skillName;
	}

	public void setSkillName(Skill skillName) {
		this.skillName = skillName;
	}
	
	public User getUserName() {
		return userName;
	}

	public void setUserName(User userName) {
		this.userName = userName;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public SkillType getType() {
		return type;
	}

	public void setType(SkillType type) {
		this.type = type;
	}
}

