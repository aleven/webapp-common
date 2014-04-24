package it.attocchi.test;

import it.attocchi.mail.utils.MailUtils;

public class TestMailUtils {

	public static void main(String[] args) {

		String addresses = "Julie.Cunico@andritz.com, amirco+marco@gmail.com, amirco+giorgio@gmail.com, amirco+mirco@gmail.com";
		
		System.out.println(MailUtils.cleanDuplicatesAndRemoveAddress2(addresses, ""));
		System.out.println(MailUtils.cleanDuplicatesAndRemoveAddress2(addresses, "amirco+giorgio@gmail.com"));
		
		System.out.println("amirco+giorgio@gmail.com".equalsIgnoreCase("amirco+giorgio@gmail.com"));
		
	}

}
