package com.project.skill_share.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.skill_share.entities.*;
import com.project.skill_share.repository.UserRepository;



@RestController
@RequestMapping("/api")
public class Authcontroller{
	@Autowired
	private UserRepository userRepo;

	 @PostMapping("/register")
	 
	 // Response entity: represents the full HTTP response.
	// <api respopnse> Yo chai body ma kasto data jane ho bhanera define garxa
	 
	 
	    public ResponseEntity<RegistResponse> registerUser(@RequestBody User user) {
	        if (userRepo.existsByUsername(user.getUsername())) {
	            return ResponseEntity.status(HttpStatus.CONFLICT)
	            		.body(new RegistResponse(false, "Username already has been taken!"));
	        }

	        userRepo.save(user);
	        return ResponseEntity.status(HttpStatus.CREATED)
	                .body(new RegistResponse(true, "User registered successfully."));
	    }

}


