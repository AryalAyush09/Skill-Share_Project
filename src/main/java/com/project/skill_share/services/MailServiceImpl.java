package com.project.skill_share.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.project.skill_share.entities.User;
import com.project.skill_share.enums.OtpPurpose;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService{
	
     private final JavaMailSender mailSender;
     
     public MailServiceImpl(JavaMailSender mailSender) {
    	 this.mailSender = mailSender;
     }
     
//     @Override
//     public void sendOtpEmail(User user, String otp, OtpPurpose purpose) {
//         String toEmail = user.getEmail();
//         String subject = purpose.getSubject();
//         String message = purpose.getFormattedMessage(user.getUsername(), otp);
//         
//         SimpleMailMessage mail = new SimpleMailMessage();
//         mail.setTo(toEmail);
//         mail.setSubject(subject);
//         mail.setText(message);
//         
//         mailSender.send(mail);
//     }
//
//}
     
@Override
public void sendOtpEmail(User user, String otp, OtpPurpose purpose) {
    String toEmail = user.getEmail();
    String subject = purpose.getSubject();
    String message = purpose.getFormattedMessage(user.getUsername(), otp);

    try {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

      
        helper.setFrom("SkillShare Security Team <arlakhil34@gmail.com>");

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(message, false);  // false means plain text

        mailSender.send(mimeMessage);
    } catch (Exception e) {
       
        e.printStackTrace();
    }
}
}

