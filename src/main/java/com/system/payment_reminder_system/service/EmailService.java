package com.system.payment_reminder_system.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("deltaofficial08@gmail.com");
        message.setTo(to);
        message.setText(text);
        message.setSubject(subject);

        mailSender.send(message);

    }
}
