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
