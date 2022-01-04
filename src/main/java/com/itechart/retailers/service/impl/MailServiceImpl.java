package com.itechart.retailers.service.impl;

import com.itechart.retailers.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;

    @Override
    public void sendPassword(String email, String password) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        String text = "Hello! Your admin password: " + password
                + "\n Please do not share it with anyone else.";
        String subject = "retailer API: password";
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);
    }
}
