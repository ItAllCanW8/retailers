package com.itechart.retailers.service;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The interface Mail service.
 */
public interface MailService {

	/**
	 * Send password.
	 *
	 * @param email    the email
	 * @param password the password
	 * @throws IOException the io exception
	 */
	void sendPassword(String email, String password) throws IOException;

	/**
	 * Send birthday congratulation.
	 *
	 * @param email the email
	 * @throws IOException the io exception
	 */
	void sendBirthdayCongratulation(String email) throws IOException;

}
