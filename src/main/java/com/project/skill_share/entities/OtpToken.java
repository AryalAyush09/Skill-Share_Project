package com.project.skill_share.entities;

import java.time.LocalDateTime;
import com.project.skill_share.enums.OtpPurpose;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "otp_table")
	
public class OtpToken{
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private long id;
		private String otp;
		private boolean used ;
		private LocalDateTime generatedAt;
		private LocalDateTime expiresAt;

		@Enumerated(EnumType.STRING)
		private OtpPurpose purpose;
		
		@ManyToOne
		@JoinColumn(name = "user_id", nullable = false)
		  private User user;
		
		
		public OtpToken() {}

		public OtpToken(String otp, User user, OtpPurpose purpose,
				LocalDateTime generatedAt, LocalDateTime expiresAt) {
		    this.otp = otp;
		    this.user = user;
		    this.purpose = purpose;
		    this.generatedAt = generatedAt;
		    this.expiresAt = expiresAt;
		    this.used = false;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getOtp() {
			return otp;
		}

		public void setOtp(String otp) {
			this.otp = otp;
		}

		public boolean isUsed() {
			return used;
		}

		public void setUsed(boolean used) {
			this.used = used;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public LocalDateTime getGeneratedAt() {
			return generatedAt;
		}

		public void setGeneratedAt(LocalDateTime generatedAt) {
			this.generatedAt = generatedAt;
		}

		public LocalDateTime getExpiresAt() {
			return expiresAt;
		}

		public void setExpiresAt(LocalDateTime expiresAt) {
			this.expiresAt = expiresAt;
		}

		public OtpPurpose getPurpose() {
			return purpose;
		}

		public void setPurpose(OtpPurpose purpose) {
			this.purpose = purpose;
		}

}
