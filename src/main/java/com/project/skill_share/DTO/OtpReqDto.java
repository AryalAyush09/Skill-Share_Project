package com.project.skill_share.DTO;

import com.project.skill_share.enums.OtpPurpose;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class OtpReqDto {
	@NotBlank(message = "Email is required")
	private String email;

	@NotNull(message = "OTP purpose is required")
	private OtpPurpose otpPurpose;

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public OtpPurpose getOtpPurpose() {
	return otpPurpose;
}

public void setOtpPurpose(OtpPurpose otpPurpose) {
	this.otpPurpose = otpPurpose;
}

}
