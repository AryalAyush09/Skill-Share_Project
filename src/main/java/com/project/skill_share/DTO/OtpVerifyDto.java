package com.project.skill_share.DTO;

import com.project.skill_share.enums.OtpPurpose;

public class OtpVerifyDto {
    private String email;
    private String otp;
    private OtpPurpose otpPurpose;
    
    
    public OtpVerifyDto() {}
    
    public OtpVerifyDto(String email, String otp, OtpPurpose otpPurpose){
    	this.email = email;
    	this.otp = otp;
    	this.otpPurpose = otpPurpose;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public OtpPurpose getOtpPurpose() {
		return otpPurpose;
	}

	public void setOtpPurpose(OtpPurpose otpPurpose) {
		this.otpPurpose = otpPurpose;
	}
    
    
}
