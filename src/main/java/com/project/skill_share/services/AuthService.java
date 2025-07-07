package com.project.skill_share.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.skill_share.DTO.LoginResponseDto;
import com.project.skill_share.DTO.LoginUserDto;
import com.project.skill_share.GlobalErrorHandler.AlreadyExistsException;
import com.project.skill_share.GlobalErrorHandler.EmailNotVerifiedException;
import com.project.skill_share.GlobalErrorHandler.InvalidCredentialsException;
import com.project.skill_share.GlobalErrorHandler.UserNotFoundException;
import com.project.skill_share.configuration.JwtUtil;
import com.project.skill_share.entities.LoginForm;
import com.project.skill_share.entities.User;
import com.project.skill_share.enums.EmailTYPE;
import com.project.skill_share.enums.Role;
import com.project.skill_share.repository.UserRepository;
import com.project.skill_share.response.GenericResponse;

@Service
public class AuthService {

	private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public AuthService(UserRepository userRepo,PasswordEncoder passwordEncoder,JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    
    public 	GenericResponse registerUser(User user) {
        if(userRepo.existsByUsername(user.getUsername())) {
        	throw new AlreadyExistsException("Username already taken!");
        }
        if (userRepo.existsByEmail(user.getEmail())) {
        	throw new AlreadyExistsException("Email already taken!");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Role.USER);
        user.setEmailStatus(EmailTYPE.PENDING);
        userRepo.save(user);
        
        return new GenericResponse(true, "User registered successfully.", null);
    }
    
    public GenericResponse loginUser(LoginForm loginForm) {
        User user = userRepo.findByEmail(loginForm.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Email not found!"));

        validatePassword(user, loginForm.getPassword());
        validateEmailVerified(user);

         String token = jwtUtil.generateToken(String.valueOf(user.getId()));
//        String token = jwtUtil.generateToken(user.getId().toString());
        
           LoginUserDto userDto = new LoginUserDto(user.getId(),user.getEmail(),	
                     user.getRoles());
           
          LoginResponseDto loginResponse = new LoginResponseDto(token,userDto);
          
        return new GenericResponse(true,"Login Successfull",loginResponse);
    }

    private void validatePassword(User user, String Password) {
        if (!passwordEncoder.matches(Password, user.getPassword())) {
            throw new InvalidCredentialsException("Incorrect password!");
        }
    }

    private void validateEmailVerified(User user) {
        if (user.getEmailStatus() != EmailTYPE.VERIFIED) {
            throw new EmailNotVerifiedException("Please verify your email before logging in.");
        }
    }
}
