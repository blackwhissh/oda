package com.startup.oda.service;

import com.startup.oda.config.LogEntryExit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class BrevoMailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BrevoMailService.class);
    @Value("${brevo.mail.username}")
    private String mailUsername;
    @Value("${brevo.mail.password}")
    private String mailPassword;
    @Value("${brevo.mail.host}")
    private String mailHost;
    @Value("${brevo.mail.port}")
    private int mailPort;

    @LogEntryExit
    public void sendMail(String to, String token) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", mailHost);
        props.put("mail.smtp.port", mailPort);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailUsername, mailPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress("blackwhissh@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Email Verification");

            String url = "http://localhost:8081/v1/api/auth/verify/" + token;
            String content = "<a href='" + url + "'>" + url + "</a>";

            message.setText("Please click on this link to verify your identity: " + content);

            Transport.send(message);

            LOGGER.info("Email sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
