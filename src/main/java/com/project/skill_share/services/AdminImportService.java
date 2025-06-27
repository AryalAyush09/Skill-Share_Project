package com.project.skill_share.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.project.skill_share.entities.User;
import com.project.skill_share.enums.EmailTYPE;
import com.project.skill_share.enums.UserType;
import com.project.skill_share.repository.UserRepository;

@Service  
public class AdminImportService {
  private final UserRepository userRepo;
  private final PasswordEncoder passEnco;
  
  public AdminImportService(UserRepository userRepo, PasswordEncoder passEnco) {
	  this.userRepo = userRepo;
	  this.passEnco =passEnco;
  }
  
  public void setUp() {
	  String adminEmail = "admin44@gmail.com";
	  if(!userRepo.existsByEmail(adminEmail)) {
		  User admin = new User();
		  admin.setUsername("admin");
		  admin.setEmail(adminEmail);
		  admin.setPassword(passEnco.encode("admin123"));
	      admin.setUserType(UserType.ADMIN);
	      admin.setEmailStatus(EmailTYPE.VERIFIED);
	      userRepo.save(admin);
	  }
  }
}
