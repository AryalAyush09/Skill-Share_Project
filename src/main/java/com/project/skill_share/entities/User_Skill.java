package com.project.skill_share.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.project.skill_share.enums.SkillType;

import jakarta.persistence.*;

@Entity
@Table(
    name = "user_skill_table",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "skill_id", "type"})
)
public class User_Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SkillType type;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

  
    public User_Skill() {}

    public User_Skill(Long id, Skill skill, User user, SkillType type, LocalDateTime createdAt) {
        this.id = id;
        this.skill = skill;
        this.user = user;
        this.type = type;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SkillType getType() {
        return type;
    }

    public void setType(SkillType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
