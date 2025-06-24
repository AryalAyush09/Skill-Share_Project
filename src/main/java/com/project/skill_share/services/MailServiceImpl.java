package com.project.skill_share.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.project.skill_share.entities.User;
import com.project.skill_share.enums.OtpPurpose;

@Service
public class MailServiceImpl implements MailService{
	
     private final JavaMailSender mailSender;
     
     public MailServiceImpl(JavaMailSender mailSender) {
    	 this.mailSender = mailSender;
     }
     
     @Override
     public void sendOtpEmail(User user, String otp, OtpPurpose purpose) {
         String toEmail = user.getEmail();
         String subject = purpose.getSubject();
         String message = purpose.getFormattedMessage(user.getUsername(), otp);
         
         SimpleMailMessage mail = new SimpleMailMessage();
         mail.setTo(toEmail);
         mail.setSubject(subject);
         mail.setText(message);
         
         mailSender.send(mail);
     }

}

