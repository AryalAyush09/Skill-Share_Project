package com.project.skill_share.OTPGenerate;

import com.project.skill_share.enums.OtpPurpose;

public class OtpUtilMessage {
	    public static String getMessage(OtpPurpose purpose) {
	        return switch (purpose) {
	            case VERIFY_EMAIL -> "Email verified successfully.";
	            case RESET_PASSWORD -> "OTP verified. You can now reset your password.";
	            default -> "OTP verified.";
	        };
	    }
	}

