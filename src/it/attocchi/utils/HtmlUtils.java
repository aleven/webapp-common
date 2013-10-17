/*
    Copyright (c) 2012 Mirco Attocchi
	
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;

public class HtmlUtils {

	protected static Logger logger = Logger.getLogger(HtmlUtils.class.getName());

	/**
	 * 
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	// public static String readUrl(String uri) throws Exception {
	// StringBuilder sb = new StringBuilder();
	// URL url = new URL(uri);
	// BufferedReader in = new BufferedReader(new
	// InputStreamReader(url.openStream()));
	//
	// String inputLine;
	// while ((inputLine = in.readLine()) != null) {
	// sb.append(inputLine);
	// }
	// in.close();
	//
	// return sb.toString();
	// }

	public static String callUrl(String url) throws Exception {
		StringBuffer sb = new StringBuffer();

		// try {

		if (StringUtils.isNotEmpty(url)) {
			URL anUrl = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(anUrl.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				// System.out.println(inputLine);
				sb.append(inputLine);
			}
			in.close();
		}

		// } catch (Exception ex) {
		// // logger.error("callProc", ex);
		// }

		return sb.toString();
	}

	/**
	 * 
	 * @param htmlText
	 * @param convertHtmlNewLine
	 *            if enabled convert <br>
	 *            and
	 *            <p>
	 *            to /n new line
	 * @return
	 */
	public static String convertoHtmlToJavaString(String htmlText, boolean convertHtmlNewLine) {
		// String res = null;

		// res = htmlText;

		// if (convertHtmlNewLine) {
		// res = res.replaceAll("\\<BR\\>", "\n");
		// }
		// res = res.replaceAll("\\<.*?\\>", "");

		// return Jsoup.parse(htmlText).text();

		String res = null;

		final String tmpPlaceHolder = "#br2n#";

		String tmp = htmlText;

		if (convertHtmlNewLine) {
			tmp = tmp.replaceAll("(?i)<br[^>]*>", tmpPlaceHolder);
			tmp = tmp.replaceAll("(?i)<p[^>]*>", tmpPlaceHolder);
		}

		res = Jsoup.parse(tmp).text();

		if (convertHtmlNewLine)
			res = res.replaceAll(tmpPlaceHolder, "\n");

		return res;

		// return res;
	}

	// public static String HtmlToJavaStringPreserveEnter(String htmlText) {
	// String res = null;
	//
	// final String tmpPlaceHolder = "#br2n#";
	//
	// String tmp = htmlText;
	//
	// tmp = htmlText.replaceAll("(?i)<br[^>]*>", tmpPlaceHolder);
	// tmp = htmlText.replaceAll("(?i)<p[^>]*>", tmpPlaceHolder);
	//
	// res = Jsoup.parse(tmp).text();
	//
	// res = res.replaceAll(tmpPlaceHolder, "\n");
	//
	// return res;
	// }

	// public static String readUrl(String uri) throws Exception {
	// Document doc = Jsoup.connect(uri).get();
	// doc.get
	// }
	
	/**
	 * Funzione che converte
	 * 
	 * @param aString
	 * @return
	 */
	public static String encodeForEscape(String aString) {
		String res = null;

		// if (aString != null) {
		if (StringUtils.isNotEmpty(aString)) {
			/*
			 * Succede che poi in HTML ho tutte le entita HTML se uso escapeHtml
			 */
			res = aString;
			// res = StringEscapeUtils.escapeHtml(aString);

			/*
			 * Per Ultimo questo altrimenti converte <BR> nelle entita' quello
			 * fatto prima
			 */
			res = res.replaceAll("\n", "<BR/>").replaceAll("\r", "");
		}

		return res;
	}

	public static String encodeWebUrl(String aString) {
		String res = null;

		// res = aString.replaceAll("(?:https?|ftps?)://[\\w/%.-]+",
		// "<a href='$0'>$0</a>");

		String regexp = "(?:https?|ftps?)://[\\w/%.-]+";

		// if (aString != null) {
		if (StringUtils.isNotEmpty(aString)) {
			res = aString.replaceAll(regexp, "<a href='$0' class='LinkChiaveEsterna' target='_blank'>$0</a>");
		}
		// res = aString.replaceAll("(?:wwws?)://[\\w/%.-]+",
		// "<a href='http://$0'>$0</a>");

		return res;
	}	
}
