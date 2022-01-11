package com.itechart.retailers.service.impl;

import com.itechart.retailers.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
	private final JavaMailSender javaMailSender;

	@Override
	public void sendPassword(String email, String password) throws IOException {
		InputStream input = new FileInputStream("src/main/resources/mailTemplate.properties");
		Properties prop = new Properties();
		prop.load(input);

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		String text = String.format(prop.getProperty("sendPasswordText"), password);
		String subject = prop.getProperty("sendPasswordSubject");
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(text);
		javaMailSender.send(simpleMailMessage);
	}

	@Override
	public void sendBirthdayCongratulation(String email) throws IOException {
		InputStream input = new FileInputStream("src/main/resources/mailTemplate.properties");
		Properties prop = new Properties();
		prop.load(input);

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		String text = prop.getProperty("sendBirthdayText");
		String subject = prop.getProperty("sendBirthdaySubject");
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(text);
		javaMailSender.send(simpleMailMessage);
	}
}
