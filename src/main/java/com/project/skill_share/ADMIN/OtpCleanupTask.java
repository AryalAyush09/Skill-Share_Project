package com.project.skill_share.ADMIN;

import java.time.LocalDateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;  // import this
import com.project.skill_share.repository.OtpRepository;

@Component
public class OtpCleanupTask {

    private final OtpRepository otpRepo;

    public OtpCleanupTask(OtpRepository otpRepo) {
        this.otpRepo = otpRepo;
    }

    // Runs every 10 minutes
    @Transactional  // Add this annotation here
    @Scheduled(fixedRate = 10 * 60 * 1000) // 10 min in milliseconds
    public void deleteExpiredOtps() {
        int deletedCount = otpRepo.deleteByExpiresAtBefore(LocalDateTime.now());
        if (deletedCount > 0) {
            System.out.println("Deleted " + deletedCount + " expired OTP(s)");
        }
    }
}
