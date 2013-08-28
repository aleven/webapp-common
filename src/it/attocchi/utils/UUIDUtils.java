package it.attocchi.utils;

import java.util.UUID;

public class UUIDUtils {

	public static String generate() {
		return UUID.randomUUID().toString();
	}
	
}
