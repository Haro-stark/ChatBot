package com.example.ChatBot.Util;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Data
public class MailUtil {
    private final JavaMailSender javaMailSender;

    public MailUtil(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /**
     * @return String which is a phone number.
     * @author Haroon Rasheed
     * @version 1.5
     * @desription This function returns the phone number against a particular user id
     * @creationDate 13 October 2021
     */
    public ResponseEntity<String> sendEmail(String recipientEmail, String emailMessage) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(recipientEmail, recipientEmail);
        msg.setSubject("Verification Token for User Registration");
        msg.setText(emailMessage);

        javaMailSender.send(msg);
        return ResponseEntity.ok().body("successfully sent");
    }
}

