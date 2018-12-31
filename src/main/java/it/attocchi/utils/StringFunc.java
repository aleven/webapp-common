/*
    Copyright (c) 2012,2013 Mirco Attocchi
	
    This file is part of WebAppCommon.

    WebAppCommon is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    WebAppCommon is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with WebAppCommon.  If not, see <http://www.gnu.org/licenses/>.
*/

package it.attocchi.utils;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>StringFunc class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class StringFunc {

	/** Constant <code>NEW_LINE="\r\n"</code> */
	public static final String NEW_LINE = "\r\n";

	/**
	 * <p>concat.</p>
	 *
	 * @param separator a {@link java.lang.String} object.
	 * @param strings a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
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

	/**
	 * <p>equalsIgnoreCase.</p>
	 *
	 * @param string1 a {@link java.lang.String} object.
	 * @param string2 a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean equalsIgnoreCase(String string1, String string2) {
		return (string1 != null && string2 != null && string1.equalsIgnoreCase(string2));
	}

	/**
	 * <p>contains.</p>
	 *
	 * @param string a {@link java.lang.String} object.
	 * @param subString a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public static boolean contains(String string, String subString) {
		return string != null && !string.isEmpty() && string.indexOf(subString) >= 0;
	}

	/**
	 * <p>readLines.</p>
	 *
	 * @param aText a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 */
	public static List<String> readLines(String aText) {
		List<String> res = null;

		if (StringUtils.isNotBlank(aText))
			res = Arrays.asList(StringUtils.split(aText, NEW_LINE));

		return res;
	}

	/**
	 * <p>writeLines.</p>
	 *
	 * @param lines a {@link java.util.List} object.
	 * @param defaultValueIfEmpty a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String writeLines(List<String> lines, String defaultValueIfEmpty) {
		String res = defaultValueIfEmpty;

		if (ListUtils.isNotEmpty(lines))
			res = StringUtils.join(lines.toArray(), NEW_LINE);

		return res;
	}
}
