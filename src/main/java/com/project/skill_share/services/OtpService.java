package com.project.skill_share.services;

import java.time.Duration;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import com.project.skill_share.GlobalErrorHandler.EmailAlreadyVerifiedException;
import com.project.skill_share.GlobalErrorHandler.EmailNotVerifiedException;
import com.project.skill_share.GlobalErrorHandler.InvalidOtpException;
import com.project.skill_share.GlobalErrorHandler.OtpAlreadyUsedException;
import com.project.skill_share.GlobalErrorHandler.OtpExpiredException;
import com.project.skill_share.GlobalErrorHandler.OtpRateLimitException;
import com.project.skill_share.GlobalErrorHandler.ResourceNotFoundException;
import com.project.skill_share.OTPGenerate.OtpUtil;
import com.project.skill_share.OTPGenerate.OtpUtilMessage;
import com.project.skill_share.configuration.JwtUtil;
import com.project.skill_share.entities.OtpToken;
import com.project.skill_share.entities.User;
import com.project.skill_share.enums.EmailTYPE;
import com.project.skill_share.enums.OtpPurpose;
import com.project.skill_share.repository.OtpRepository;
import com.project.skill_share.repository.UserRepository;
import com.project.skill_share.response.GenericResponse;

import jakarta.transaction.Transactional;

@Service	
public class OtpService {
	private final UserRepository userRepo;
	private final OtpRepository otpRepo;
	private final MailService mailService;
	private final JwtUtil jwtUtil;
	
	public OtpService(UserRepository userRepo,OtpRepository otpRepo,
			MailService mailService, JwtUtil jwtUtil) {
		this.userRepo = userRepo;
		this.otpRepo = otpRepo;
		this.mailService = mailService;
		this.jwtUtil = jwtUtil;
	}
	
	@Transactional
    public  GenericResponse generateOtpForUsers(String email, OtpPurpose otpPurpose) {
		LocalDateTime now = LocalDateTime.now();

	  User user = userRepo.findByEmail(email)
			  .orElseThrow(() -> new ResourceNotFoundException
					  ("User Not Found with that Email:" + email));
	   
		  if (otpPurpose == OtpPurpose.VERIFY_EMAIL &&
				  user.getEmailStatus() == EmailTYPE.VERIFIED) {
             throw new EmailAlreadyVerifiedException("Email is already verified."); 
             }
		  
		  if (otpPurpose == OtpPurpose.RESET_PASSWORD && 
				  user.getEmailStatus() == EmailTYPE.PENDING) {
			  throw new EmailNotVerifiedException("Email is not verified!");
		  }
		    
	    otpRepo.deleteAllByUserAndPurposeAndExpiresAtBefore
	        (user, otpPurpose,now);

      //  Fetch latest OTP for this user & purpose
      OtpToken latestOtp = otpRepo.findTopByUserAndPurposeOrderByGeneratedAtDesc
    		  (user, otpPurpose);
      
      //  Check if OTP exists and cooldown period passedx`
      if (latestOtp != null) {
          long secondsSinceLastOtp = Duration.between
        		  (latestOtp.getGeneratedAt(), now).getSeconds();
          int cooldownSec = 120; // example 2 minutes cooldown
          
          //  If cooldown not passed throw exception
          if (secondsSinceLastOtp < cooldownSec) {
              throw new OtpRateLimitException("Please wait before requesting another OTP.");
          }
      }
      
      String newOtp = OtpUtil.generateRandomOtp(6);
      OtpToken otpToken = new OtpToken(newOtp, user, otpPurpose, now, now.plusMinutes(5));
      	
	   otpRepo.save(otpToken);
	   mailService.sendOtpEmail(user, newOtp, otpPurpose);  
	   return new GenericResponse
			   (true, "OTP sent successfully to email: " + user.getEmail(), null);
   }
   
	@Transactional
	public GenericResponse verifyOtp(String email, String otp, OtpPurpose otpPurpose) {
	    User user = userRepo.findByEmail(email)
	            .orElseThrow(() -> new ResourceNotFoundException("Email not found: " + email));

	    OtpToken latestOtp = otpRepo.findTopByUserAndPurposeOrderByGeneratedAtDesc(user, otpPurpose);

	    if (latestOtp == null) {
	        throw new ResourceNotFoundException("No OTP found for this user");
	    }
	    
	    if (!latestOtp.getOtp().equals(otp)) {
	        throw new InvalidOtpException("Incorrect OTP.");
	    }
	    
	    if (latestOtp.getExpiresAt().isBefore(LocalDateTime.now())) {
	        throw new OtpExpiredException("OTP has expired. Please request a new one.");
	    }

	    if (latestOtp.isUsed()) {
	        throw new OtpAlreadyUsedException("This OTP has already been used.");
	    }

	    latestOtp.setUsed(true);
	    otpRepo.save(latestOtp);

	    if (otpPurpose == OtpPurpose.VERIFY_EMAIL) {
	        user.setEmailStatus(EmailTYPE.VERIFIED);
	        userRepo.save(user);
	        return new GenericResponse(true, "Email verified successfully", null);
	    }
	    
	    if (otpPurpose == OtpPurpose.RESET_PASSWORD) {
	        String resetToken = jwtUtil.generateResetToken(user.getEmail());
	        return new GenericResponse(true, "Reset token generated", resetToken);
	    }
	    
	    return new GenericResponse(false, "Unhandled OTP purpose", null);
	}
}

