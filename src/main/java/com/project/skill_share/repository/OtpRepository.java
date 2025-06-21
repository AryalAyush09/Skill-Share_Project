package com.project.skill_share.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.skill_share.entities.OtpToken;
import com.project.skill_share.entities.User;
import com.project.skill_share.enums.OtpPurpose;

public interface OtpRepository extends JpaRepository<OtpToken, Long>{
	OtpToken findTopByUserAndPurposeOrderByGeneratedAtDesc
	(User user, OtpPurpose otpPurpose);
	
	 void deleteAllByUserAndPurposeAndExpiresAtBefore
	 (User user, OtpPurpose purpose, LocalDateTime time);
	 
	 Optional<OtpToken> findTopByOtpOrderByGeneratedAtDesc(String otp);

}
