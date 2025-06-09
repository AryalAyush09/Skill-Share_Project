package com.project.skill_share.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "skill_table")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(nullable = false, unique = true)
    private String skillName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Skill() {}

    public Skill(String skillName) {
        this.skillName = skillName;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
