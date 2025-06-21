//package com.project.skill_share.ADMIN;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.project.skill_share.repository.UserRepository;
//
//import jakarta.annotation.PostConstruct;
//
//@Service
//public class AdminService {
//
//	@Autowired
//	private UserRepository userRepository;
//
//	@PostConstruct
//	public void setup(User user) {
//		System.out.println("This setup() is called automatically after object construction");
//		
//		// write code for create ADMIN user.
//		if (userRepository.count() == 0) {
//			User admin = new User();
//			admin.setUsername("admin");
//			admin.setEmail("admin@demo.com");
//			admin.setPassword("admin123");
//			admin.setType(UserType.ADMIN);
//			userRepository.save(admin);
//		}
//
//	
//
//
