package com.project.skill_share.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
	
 @PostMapping("/send/otp/{purpose}")
 public ResponseEntity<?> sendOtp(@PathVariable String purpose,
		   @Valid @RequestBody EmailDto dto){
	 try {
	        OtpPurpose otpPurpose = OtpPurpose.valueOf(purpose.toUpperCase());
	        return ResponseEntity.ok(otpService.generateOtpForUsers(dto.getEmail(), otpPurpose));
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body("Invalid OTP purpose: " + purpose);
	    }
 }

 @PostMapping("verify/otp/{purpose}")
 public ResponseEntity<?> verifyOtp(@PathVariable String purpose, 
		         @RequestBody OtpVerifyDto dto){
	 try {
	        OtpPurpose otpPurpose = OtpPurpose.valueOf(purpose.toUpperCase()); // or use fromString()
	        return ResponseEntity.ok(
	            otpService.verifyOtp(dto.getEmail(), dto.getOtp(), otpPurpose));
	   
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body("Invalid OTP purpose: " + purpose);
	    }
  }
 }