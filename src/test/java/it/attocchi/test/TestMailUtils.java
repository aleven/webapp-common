package it.attocchi.test;

import it.attocchi.mail.utils.MailUtils;

public class TestMailUtils {

	public static void main(String[] args) {

		String addresses = "test@email.com";

		System.out.println(MailUtils.cleanDuplicatesAndRemoveAddress2(addresses, ""));
		System.out.println(MailUtils.cleanDuplicatesAndRemoveAddress2(addresses, "test@email.com"));

		System.out.println("test@email.com".equalsIgnoreCase("test@email.com"));
	}

}
