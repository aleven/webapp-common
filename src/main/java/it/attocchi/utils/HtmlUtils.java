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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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
		String res = aString;

		// if (aString != null) {
		if (StringUtils.isNotEmpty(res)) {
			/*
			 * Succede che poi in HTML ho tutte le entita HTML se uso escapeHtml
			 */
			// res = aString;
			// res = StringEscapeUtils.escapeHtml(aString);

			/*
			 * Per Ultimo questo altrimenti converte <BR> nelle entita' quello
			 * fatto prima
			 */
			// res = res.replaceAll("\n", "<br/>").replaceAll("\r", "");
			res = res.replaceAll("(\r\n|\n)", "<br />");
		}

		return res;
	}

	public static String encodeWebUrl(String aString) {
		String res = null;

//		// res = aString.replaceAll("(?:https?|ftps?)://[\\w/%.-]+",
//		// "<a href='$0'>$0</a>");
//
//		String regexp = "(?:https?|ftps?)://[\\w/%.-]+";
//
//		/**
//		 * http://www.regexguru.com/2008/11/detecting-urls-in-a-block-of-text/
//		 */
//		// regexp = "\\b(?:(?:https?|ftp|file)://|www\\.|ftp\\.)[-A-Z0-9+&@#/%=~_|$?!:,.]*[A-Z0-9+&@#/%=~_|$]";
//
//		// if (aString != null) {
//		if (StringUtils.isNotEmpty(aString)) {
//			res = aString.replaceAll(regexp, "<a href='$0' class='LinkChiaveEsterna' target='_blank'>$0</a>");
//		}
//		// res = aString.replaceAll("(?:wwws?)://[\\w/%.-]+",
//		// "<a href='http://$0'>$0</a>");

		// separate input by spaces ( URLs don't have spaces )
		String[] parts = aString.split("\\s+");
		// aggiungo gli spazi per garantire il replace delle parole (link esatti)
		res = aString;
		// Attempt to convert each item into an URL.
//		StringBuffer concat = new StringBuffer();
		Map<String, String> bookmarks = new HashMap<String, String>();
		int count = 0;
		for (String item : parts) {
			try {
				URL url = new URL(item);
				count++;
				final String key = "###" + count + "###";
				// If possible then replace with anchor...
				// res = res.replace(item, "<a href=\"" + url + "\" class=\"LinkChiaveEsterna\" target=\"_blank\">" + url + "</a>");
				res = res.replace(item, key); // replaceFirst usa regular in caso di link con simbolo + sembra non funzionare
				bookmarks.put(key, "<a href=\"" + url + "\" class=\"LinkChiaveEsterna\" target=\"_blank\">" + url + "</a>");
				
				// il replace su tutto il testo soffre di un problema nel caso ci siano due link simili con parametri diversi il secondo replace lavora anche sul primo
				
//				concat.append("<a href=\"" + url + "\" class=\"LinkChiaveEsterna\" target=\"_blank\">" + url + "</a>");
//				concat.append(" ");
			} catch (MalformedURLException e) {
				// If there was an URL that was not it!...
				// System.out.print(item + " ");
//				concat.append(item);
//				concat.append(" ");
			}
		}
		for (String key : bookmarks.keySet()) {
			res = res.replace(key, bookmarks.get(key));
		}
		return res;
//		return concat.toString().trim();
	}
}
