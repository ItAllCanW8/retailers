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
	public static final String PASSWORD_PROPERTY = "sendPasswordText";
	public static final String PASSWORD_SUBJECT_PROPERTY = "sendPasswordSubject";
	public static final String MAIL_TEMPLATE_PATH = "src/main/resources/mailTemplate.properties";
	public static final String BIRTHDAY_TEXT_PROPERTY = "sendBirthdayText";
	public static final String BIRTHDAY_SUBJECT_PROPERTY = "sendBirthdaySubject";
	private final JavaMailSender javaMailSender;

	@Override
	public void sendPassword(String email, String password) throws IOException {
		InputStream input = new FileInputStream(MAIL_TEMPLATE_PATH);
		Properties prop = new Properties();
		prop.load(input);

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		String text = String.format(prop.getProperty(PASSWORD_PROPERTY), password);
		String subject = prop.getProperty(PASSWORD_SUBJECT_PROPERTY);
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(text);
		javaMailSender.send(simpleMailMessage);
	}

	@Override
	public void sendBirthdayCongratulation(String email) throws IOException {
		InputStream input = new FileInputStream(MAIL_TEMPLATE_PATH);
		Properties prop = new Properties();
		prop.load(input);

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		String text = prop.getProperty(BIRTHDAY_TEXT_PROPERTY);
		String subject = prop.getProperty(BIRTHDAY_SUBJECT_PROPERTY);
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(text);
		javaMailSender.send(simpleMailMessage);
	}
}
