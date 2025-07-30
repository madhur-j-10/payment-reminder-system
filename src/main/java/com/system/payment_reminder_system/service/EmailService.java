package com.system.payment_reminder_system.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("deltaofficial08@gmail.com");
        message.setTo(to);
        message.setText(text);
        message.setSubject(subject);

        javaMailSender.send(message);

    }

    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true = HTML

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // or use proper logging
        }
    }
}
