package it.attocchi.utils;

/**
 * 
 * @author Mirco Attocchi
 * 
 */
public class FormatUtils {

	/**
	 * Format a Strings like a C# Formatter with placeholder like {0}
	 * @param aString
	 * @param param
	 * @return
	 */
	public static String formatLikeCSharp(boolean parseForSQL, String aString, String... param) {
		String res = aString;

		int i = 0;
		for (String p : param) {

			String placeHolder = "{" + i + "}";
			if (parseForSQL)
				res = res.replace(placeHolder, javaStringToSql(p));
			else
				res = res.replace(placeHolder, p);
			i++;
		}

		return res;
	}

	private static String javaStringToSql(String aString) {
		// return aString.replace("\\", "\\\\").replace("'", "''");
		return aString.replace("'", "''");
	}
}
