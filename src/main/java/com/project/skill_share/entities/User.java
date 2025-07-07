package com.project.skill_share.entities;

import java.time.LocalDateTime;
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

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String contactNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserImage> images;
    
    @Column(name = "github_link")
    private String gitHub;

    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @OneToOne
    @JoinColumn(name = "profile_image_id")
    private UserImage profileImage;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserCV userCV;

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

	public String getGitHub() {
		return gitHub;
	}

	public UserImage getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(UserImage profileImage) {
		this.profileImage = profileImage;
	}

	public void setGitHub(String gitHub) {
		this.gitHub = gitHub;
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

}
