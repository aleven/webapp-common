/*
    Copyright (c) 2007,2014 Mirco Attocchi
	
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

package it.webappcommon.test;

import it.webappcommon.lib.DateUtils;
import it.webappcommon.lib.StringHelper;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

/**
 * 
 * @author Mirco
 */
public class TestUtils {

	protected static Logger logger = Logger.getLogger(TestUtils.class.getName());

	// private static final String REGEX = "\\bdog\\b";
	// private static final String INPUT = "dog dog dog doggie dogg";

	// private static String REGEX = "a*b";
	// private static String INPUT = "aabfooaabfooabfoob";
	// private static String REPLACE = "-";

	// private static String REGEX = "<TAG\\b[^>]*>(.*?)</TAG>";
	// private static String INPUT = "<TAG>one<TAG>two</TAG>one</TAG>";
	// private static String REPLACE = "-";

	private static String REGEX = "This message(.*?)Thank You.";
	private static String INPUT = "Your Text";
	private static String REPLACE = "-";

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {

		testURLUtils();

	}

	private static void testDateUtils() {

		// System.out.println(DateUtils.differenzaInGiorni(new Date(), new
		// Date()));
		//
		// for (Date data : DateUtils.calcolaDateIntermedie(new
		// Date(2010,10,15), new Date(2010,10,18))) {
		// System.out.println(data);
		// }

		Date test1 = new Date(2010, 10, 10, 12, 00);
		Date test2 = new Date(2010, 10, 10, 12, 15);
		Date test3 = new Date(2010, 10, 10, 11, 30);
		Date test4 = new Date(2010, 10, 10, 12, 30);

		System.out.println(DateUtils.verificaSovrapposizione(test1, test2, test3, test4, false));
		System.out.println(DateUtils.verificaSovrapposizione(test1, test2, test3, test4, true));

	}

	private static void testJSFHelper() {
		// System.out.println(HtmlUtils.encodeWebUrl("www.google.com"));
		// System.out.println(HtmlUtils.encodeWebUrl("http://localhost:8080/atreeflow/Auth/frames.jspx"));
		//
		// System.out.println(HtmlUtils.encodeWebUrl("http://www.google.com"));
		// System.out.println(HtmlUtils.encodeWebUrl("https://www.google.com"));
		// System.out.println(HtmlUtils.encodeWebUrl("ftps://www.google.com"));
	}

	private static void testStringHelper() {

		System.out.println(StringHelper.stringPart("AABBCCDDEE", 8));
		System.out.println(StringHelper.stringPart("AABBCCDDEE", 5));
		System.out.println(StringHelper.stringPart("AABBCCDDEE", 10));
		System.out.println(StringHelper.stringPart("AABBCCDDEE", 2));
		System.out.println(StringHelper.stringPart("AABBCCDDEE", 1));
		System.out.println(StringHelper.stringPart("AABBCCDDEE", 0));
		System.out.println(StringHelper.stringPart("", 0));

	}

	private static void testRegEx() {

		Pattern p = Pattern.compile(REGEX);
		Matcher m = p.matcher(INPUT);

		System.out.println(INPUT.replaceAll(REGEX, REPLACE));
	}

	private static void testSize() {
		// System.out.println(FileUtils.parseSize(1000));
		// System.out.println(FileUtils.parseSize(5000));
		// System.out.println(FileUtils.parseSize(50000));
		// System.out.println(FileUtils.parseSize(75000));
		// System.out.println(FileUtils.parseSize(1250000));
		// System.out.println(FileUtils.parseSize(225000000));
		// System.out.println(FileUtils.parseSize(202400000000l));
	}

	private static void testURLUtils() {
		// System.out.println(URLUtils.callProc("http://www.google.com"));
		// System.out.println(HtmlHelper.callUrl("http://192.168.0.230:8080/AFWHMSync/main.xhtml?do"));
	}
}
