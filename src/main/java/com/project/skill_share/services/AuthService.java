package com.project.skill_share.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.skill_share.DTO.LoginResponseDto;
import com.project.skill_share.DTO.LoginUserDto;
import com.project.skill_share.DTO.UserRegisterRequestDto;
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
import com.project.skill_share.response.ApiResponse;

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
    
    public 	ApiResponse<?> registerUser(UserRegisterRequestDto requestDto) {
        if(userRepo.existsByUsername(requestDto.getUsername())) {
        	throw new AlreadyExistsException("Username already taken!");
        }
        if (userRepo.existsByEmail(requestDto.getEmail())) {
        	throw new AlreadyExistsException("Email already taken!");
        }
        
        User user = new User();
        user.setFullName(requestDto.getFullName());
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setContactNumber(requestDto.getContactNumber());
        user.setRoles(Role.USER);
        user.setEmailStatus(EmailTYPE.PENDING);
        userRepo.save(user);
        
        return new ApiResponse<>(true, "User registered successfully.", null);
    }
    
    public ApiResponse<?> loginUser(LoginForm loginForm) {
        User user = userRepo.findByEmail(loginForm.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Email not found!"));

        validatePassword(user, loginForm.getPassword());
        validateEmailVerified(user);

         String token = jwtUtil.generateToken(String.valueOf(user.getId()), user.getRoles());
//        String token = jwtUtil.generateToken(user.getId().toString());
        
           LoginUserDto userDto = new LoginUserDto(user.getId(),user.getEmail(),	
                     user.getRoles());
           
          LoginResponseDto loginResponse = new LoginResponseDto(token,userDto);
          
        return new ApiResponse<>(true,"Login Successfull",loginResponse);
    }

    private void validatePassword(User user, String inputPassword) {
        System.out.println("Raw input password: " + inputPassword);
        System.out.println("Encoded password from DB: " + user.getPassword());
        
        boolean matched = passwordEncoder.matches(inputPassword, user.getPassword());
        System.out.println("Password matched? " + matched);

        if (!matched) {
            throw new InvalidCredentialsException("Incorrect nothingcrenditail");
        }
    }


    private void validateEmailVerified(User user) {
        if (user.getEmailStatus() != EmailTYPE.VERIFIED) {
            throw new EmailNotVerifiedException("Please verify your email before logging in.");
        }
    }
}
