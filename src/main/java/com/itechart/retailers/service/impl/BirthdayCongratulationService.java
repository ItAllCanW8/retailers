package com.itechart.retailers.service.impl;

import com.itechart.retailers.model.entity.User;
import com.itechart.retailers.repository.UserRepository;
import com.itechart.retailers.service.MailService;
import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BirthdayCongratulationService {
	private final UserRepository userRepository;
	private final MailService mailService;

	@Scheduled(cron = "0 0 12 * * ?")
	public void sendBirthdayCongratulation() {
		List<User> users = userRepository.findUsersByBirthday();
		if (users.size() != 0) {
			users.forEach(user -> {
				try {
					mailService.sendBirthdayCongratulation(user.getEmail());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}
}
