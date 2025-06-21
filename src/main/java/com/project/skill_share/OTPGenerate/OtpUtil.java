package com.project.skill_share.OTPGenerate;

import java.security.SecureRandom;

public class OtpUtil {

	    public static String generateRandomOtp(int length) {
	        String numbers = "0123456789";
	        SecureRandom random = new SecureRandom();
	        StringBuilder otp = new StringBuilder();

	        for (int i = 0; i < length; i++) {
	            int index = random.nextInt(numbers.length());
	            otp.append(numbers.charAt(index));
	        }
	        return otp.toString();
	    }
	}

