package com.example.demospringsecurityclient.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Email {
   @Autowired
   JavaMailSender mailSender;
   public void sendMail(String to, String subject, String body) {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom("abdallahammar323@gmail.com");
      message.setTo(to);
      message.setText(body);
      message.setSubject(subject);
      mailSender.send(message);
   }
}

