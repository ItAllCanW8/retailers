package com.itechart.retailers.service.impl;

import com.itechart.retailers.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class PasswordEmailSender extends Thread {

    private final String email;
    private final String generatedPassword;
    private final MailService mailService;

    @SneakyThrows
    @Override
    public void run() {
        mailService.sendPassword(email, generatedPassword);
    }
}
