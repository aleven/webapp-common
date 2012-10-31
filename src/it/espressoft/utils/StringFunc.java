package it.espressoft.utils;

import org.apache.commons.lang3.StringUtils;

public class StringFunc {

	public static String concat(String separator, String... strings) {
		StringBuilder sb = new StringBuilder();

		for (String aString : strings) {
			if (StringUtils.isNotBlank(aString)) {
				if (sb.length() > 0)
					sb.append(separator);
				sb.append(aString);
			}
		}

		return sb.toString();
	}

	public static boolean equalsIgnoreCase(String string1, String string2) {
		return (string1 != null && string2 != null && string1.equalsIgnoreCase(string2));
	}

}
