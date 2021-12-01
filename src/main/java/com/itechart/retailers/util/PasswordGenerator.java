package com.itechart.retailers.util;

import org.apache.commons.text.RandomStringGenerator;

public class PasswordGenerator {

	public static String generatePassword(int length) {
		RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(33, 45)
				.build();
		return pwdGenerator.generate(length);
	}

}
