package it.attocchi.utils;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class StringFunc {

	public static final String NEW_LINE = "\r\n";

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

	public static boolean contains(String string, String subString) {
		return string != null && !string.isEmpty() && string.indexOf(subString) >= 0;
	}

	public static List<String> readLines(String aText) {
		List<String> res = null;

		if (StringUtils.isNotBlank(aText))
			res = Arrays.asList(StringUtils.split(aText, NEW_LINE));

		return res;
	}

	public static String writeLines(List<String> lines, String defaultValueIfEmpty) {
		String res = defaultValueIfEmpty;

		if (ListUtils.isNotEmpty(lines))
			res = StringUtils.join(lines.toArray(), NEW_LINE);

		return res;
	}
}
