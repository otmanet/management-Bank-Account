package com.example.demo.Service;

import com.example.demo.dto.UserRegisterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSendService {

    @Autowired
    private JavaMailSender javaMailSender;

    public EmailSendService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(UserRegisterService userRegisterService) {
        String from = "Account_Gestion_Ensa@gmail.com";

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(userRegisterService.getEmail());
        message.setSubject("Welcome " + userRegisterService.getUsername());
        message.setText("test mail");
        javaMailSender.send(message);
        System.out.println("mail send success");
    }
}
