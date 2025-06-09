package com.project.skill_share.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.skill_share.GlobalErrorHandler.AlreadyExistsException;
import com.project.skill_share.GlobalErrorHandler.ResourceNotFoundException;
import com.project.skill_share.configuration.JwtUtil;
import com.project.skill_share.entities.LoginForm;
import com.project.skill_share.entities.User;
import com.project.skill_share.repository.UserRepository;
import com.project.skill_share.response.GenericResponse;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public 	GenericResponse registerUser(User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
        	throw new AlreadyExistsException("Username already taken!");
        }
        else if (userRepo.existsByEmail(user.getEmail())) {
        	throw new AlreadyExistsException("Email already taken!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        
        return new GenericResponse(true, "User registered successfully.", null);
    }
    
    public GenericResponse loginUser(LoginForm loginForm) {
//    	User user = userRepo.findByUsername(loginForm.getUserName())
//    		    .orElseThrow(() -> new ResourceNotFoundException("User not found!!"));

    	User user = userRepo.findByEmail(loginForm.getEmail())
    		    .orElseThrow(() -> new ResourceNotFoundException("Email not found!!"));
    	
            if (!passwordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
            	throw new ResourceNotFoundException("Password Incorrect!!!");
            }
            	String token = jwtUtil.generateToken(user.getUsername());  // Generate JWT
                return new GenericResponse(true, "User login successfully!",token);
            }
}
