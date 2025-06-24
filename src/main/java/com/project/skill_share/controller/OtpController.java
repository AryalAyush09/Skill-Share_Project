package com.project.skill_share.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.skill_share.DTO.EmailDto;
import com.project.skill_share.DTO.OtpVerifyDto;
import com.project.skill_share.enums.OtpPurpose;
import com.project.skill_share.services.OtpService;
import jakarta.validation.Valid;

 @RestController
 @RequestMapping("/api")

public class OtpController {

	private final OtpService otpService;
	
	public OtpController(OtpService otpService) {
		this.otpService = otpService;
	}

 @PostMapping("/send/email-verification")
 public ResponseEntity<?> sendVerificationOtp(@Valid @RequestBody EmailDto dto) {
	return ResponseEntity.ok(
		otpService.generateOtpForUsers(dto.getEmail(), OtpPurpose.VERIFY_EMAIL));
 }

 @PostMapping("/send/reset-password")
 public ResponseEntity<?> sendResetPasswordOtp(@Valid @RequestBody EmailDto dto) {
	return ResponseEntity.ok(
		otpService.generateOtpForUsers(dto.getEmail(), OtpPurpose.RESET_PASSWORD));
 }

 @PostMapping("/verify/email-otp")
 public ResponseEntity<?> verifyEmailOtp(@Valid @RequestBody OtpVerifyDto dto) {
	return ResponseEntity.ok(
		otpService.verifyOtp(dto.getEmail(), dto.getOtp(), OtpPurpose.VERIFY_EMAIL));
 }

 @PostMapping("/verify/reset-password-otp")
 public ResponseEntity<?> verifyResetOtp(@Valid @RequestBody OtpVerifyDto dto) {
	return ResponseEntity.ok(
		otpService.verifyOtp(dto.getEmail(), dto.getOtp(), OtpPurpose.RESET_PASSWORD));
  }
 }