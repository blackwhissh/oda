package com.startup.oda.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSenderImpl javaMailSender;

    public MailService(JavaMailSenderImpl javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String recipient, String token){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("auth@oda.ge");
        message.setTo(recipient);
        message.setSubject("Email Verification");
        String url = "http://localhost:8081/v1/api/auth/verify/" + token;
        String content="<a href='"+url+"'>"+url+"</a>";
        message.setText("Please click on this link to verify your identity: " + content);
        javaMailSender.send(message);
    }
}
