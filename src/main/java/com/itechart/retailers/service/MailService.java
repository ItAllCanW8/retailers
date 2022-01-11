package com.itechart.retailers.service;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface MailService {

	void sendPassword(String email, String password) throws IOException;

	void sendBirthdayCongratulation(String email) throws IOException;

}
