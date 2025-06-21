package com.project.skill_share.services;

import com.project.skill_share.entities.User;
import com.project.skill_share.enums.OtpPurpose;

public interface MailService {
    void sendOtpEmail(User user, String otp, OtpPurpose purpose);
}
