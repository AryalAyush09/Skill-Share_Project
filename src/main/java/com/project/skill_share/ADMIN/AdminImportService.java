package com.project.skill_share.ADMIN;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.skill_share.entities.User;
import com.project.skill_share.enums.EmailTYPE;
import com.project.skill_share.enums.Role;
import com.project.skill_share.repository.UserRepository;

@Service
public class AdminImportService implements CommandLineRunner{
  private final UserRepository userRepo;
  private final PasswordEncoder passEnco;
  
  public AdminImportService(UserRepository userRepo, PasswordEncoder passEnco) {
	  this.userRepo = userRepo;
	  this.passEnco =passEnco;
  }
  
  @Override
   public void run(String... args) {
	  String adminEmail = "admin44@gmail.com";
	  if(!userRepo.existsByEmail(adminEmail)) {
		  User admin = new User();
		  admin.setUsername("admin");
		  admin.setEmail(adminEmail);
		  admin.setFullName("Admin");
		  admin.setPassword(passEnco.encode("@Dmin123"));
		  admin.setContactNumber("9862907680");
		  admin.setRoles(Role.ADMIN); 
	      admin.setEmailStatus(EmailTYPE.VERIFIED);
	      userRepo.save(admin);
	  }
  }
}
