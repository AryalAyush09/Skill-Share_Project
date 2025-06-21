package com.project.skill_share.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.skill_share.DTO.OtpReqDto;
import com.project.skill_share.DTO.OtpVerifyDto;
import com.project.skill_share.services.OtpService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")

public class OtpController {

	private final OtpService otpService;
	
	public OtpController(OtpService otpService) {
		this.otpService = otpService;
	}

	@PostMapping("/send/otp")
	public ResponseEntity<?> sendOtp(@Valid @RequestBody OtpReqDto otpReqDto) {
	    return ResponseEntity.ok(otpService.generateOtpForUsers(
	         otpReqDto.getEmail(), otpReqDto.getOtpPurpose()));
	}

	@PostMapping("/verify/otp")
	public ResponseEntity<?> verifyOtp(@Valid @RequestBody OtpVerifyDto dto){
	    return ResponseEntity.ok
	       (otpService.verifyOtp(dto.getEmail(), dto.getOtp(), dto.getOtpPurpose()));	
	}
}
