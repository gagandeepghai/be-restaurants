package com.hungerbash.restaurants.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
    @Autowired
    public JavaMailSender emailSender;
 
    @Async
    public void sendSimpleMessage(
      String to, String subject, String messageText) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(messageText);
        
        emailSender.send(message);
    }
}