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
