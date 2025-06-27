package com.project.skill_share.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.project.skill_share.GlobalErrorHandler.AlreadyExistsException;
import com.project.skill_share.GlobalErrorHandler.EmailNotVerifiedException;
import com.project.skill_share.GlobalErrorHandler.ResourceNotFoundException;
import com.project.skill_share.GlobalErrorHandler.TokenExpiredException;
import com.project.skill_share.configuration.JwtUtil;
import com.project.skill_share.entities.User;
import com.project.skill_share.enums.EmailTYPE;
import com.project.skill_share.repository.UserRepository;
import com.project.skill_share.response.GenericResponse;
import jakarta.transaction.Transactional;

public class PasswordService {
   private final UserRepository userRepo;
   private final JwtUtil jwtUtil;
   private PasswordEncoder passEnco;
   
   public PasswordService(UserRepository userRepo,JwtUtil jwtUtil,PasswordEncoder passEnco) {
	   this.userRepo = userRepo;
	   this.jwtUtil = jwtUtil;
	   this.passEnco = passEnco;
   }
   
   @Transactional 
   public GenericResponse resetPassword(String ResetToken, String newPassword) {
	   String email = jwtUtil.extractUsername(ResetToken);
	   boolean isValid = jwtUtil.validateToken(ResetToken, email);
	   
	   if (!isValid) {
	      throw new TokenExpiredException("Invalid or expired token.");
	   }
  
	   User user = userRepo.findByEmail(email)
			   .orElseThrow(()-> new ResourceNotFoundException("User not Found:" + email));
	   
	   
	   if(user.getEmailStatus() != EmailTYPE.VERIFIED) {
		   throw new EmailNotVerifiedException("Email is Not Verified");
	   }
	   
	   if (passEnco.matches(newPassword, user.getPassword())) {
	        throw new AlreadyExistsException("New password must be different from the old one.");
	    }
	   
	   user.setPassword(passEnco.encode(newPassword));
       userRepo.save(user);
       
	  return new GenericResponse(true,"Password Reset Succesfully",null);
   }
}

