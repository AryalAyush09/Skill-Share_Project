package com.project.skill_share.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import com.project.skill_share.enums.EmailTYPE;
import com.project.skill_share.enums.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = false)
    private String fullName;
    
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String contactNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserImage> images;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SocialLink> socialLinks = new ArrayList<>();
    
    @OneToOne
    @JoinColumn(name = "profile_image_id")
    private UserImage profileImage;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private UserCV userCV;
    
    @Column(name = "average_rating")
    private Double averageRating = 0.0;

    @Column(name = "total_ratings")
    private Integer totalRatings = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmailTYPE emailStatus;
    
    @Enumerated(EnumType.STRING)
	@Column(nullable = false)
    private Role roles;
 
	public User() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

	public EmailTYPE getEmailStatus() {
		return emailStatus;
	}

	public void setEmailStatus(EmailTYPE emailStatus) {
		this.emailStatus = emailStatus;
	}

	public List<UserImage> getImages() {
		return images;
	}

	public void setImages(List<UserImage> images) {
		this.images = images;
	}

	public UserImage getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(UserImage profileImage) {
		this.profileImage = profileImage;
	}

	public Role getRoles() {
		return roles;
	}

    public UserCV getUserCV() {
        return userCV;
    }
    public void setUserCV(UserCV userCV) {
        this.userCV = userCV;
    }
	public void setRoles(Role roles) {
		this.roles = roles;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public List<SocialLink> getSocialLinks() {
		return socialLinks;
	}


	public void setSocialLinks(List<SocialLink> socialLinks) {
		this.socialLinks = socialLinks;
	}


	public Double getAverageRating() {
		return averageRating;
	}


	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}


	public Integer getTotalRatings() {
		return totalRatings;
	}


	public void setTotalRatings(Integer totalRatings) {
		this.totalRatings = totalRatings;
	}

}
