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

package it.webappcommon.lib;

import it.attocchi.utils.ListUtils;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class StringHelper {

	protected static Logger logger = Logger.getLogger(StringUtils.class.getName());

	/**
	 * Ritorna una String troncata al numero di caratteri specificati. Se la
	 * string e' piu' lunga della dimensione a cui la funzione deve troncare
	 * vengono accodati dei "..." ad indicare che la stringa continua
	 * 
	 * @return stringa troncata
	 */
	public static String stringPart(String aString, int part) {
		String res = null;

		if (aString != null && !aString.isEmpty()) {
			if (aString.length() > part) {

				if (part > 3) {
					res = aString.substring(0, part - 3);
					res = res + "...";
				} else {
					res = aString.substring(0, 3 - part);
					res = res + "...".substring(0, part - res.length());
				}

			} else {
				res = aString;
			}
		}

		return res;
	}

//	public static String concat(String... strings) {
//
////		String res = null;
////
////		StringBuilder sb = null;
////		// if (str1 != null || str2 != null || str3 != null || str4 != null ||
////		// str5 != null) {
////		if (strings != null) {
////			for (String str : strings) {
////
////				if (str != null) {
////					if (sb == null)
////						sb = new StringBuilder();
////
////					if (sb.length() > 0) {
////						sb.append(" ");
////					}
////
////					sb.append(str.trim());
////				}
////
////			}
////
////			if (sb != null && sb.length() > 0) {
////				res = sb.toString();
////			}
////		}
//		
//		return concat(" ", strings);
//	}

	public static String calculateWithSeparators(String string1, String separator1, String string2, String separator2) {

		String res = null;

		StringBuilder sb = new StringBuilder();
		// if (str1 != null || str2 != null || str3 != null || str4 != null ||
		// str5 != null) {
		// if (strings != null) {
		// for (String str : strings) {
		//
		// if (str != null) {
		// if (sb == null)
		// sb = new StringBuilder();
		//
		// if (sb.length() > 0) {
		// sb.append(" ");
		// }
		//
		// sb.append(str.trim());
		// }
		//
		// }
		//

		// }
		if (string1 != null && !string1.isEmpty()) {
			sb.append(string1 + separator1);
		}
		if (string2 != null && !string2.isEmpty()) {
			sb.append(string2 + separator2);
		}
		// if (string3 != null && !string3.isEmpty()) {
		// sb.append(string3 + separator3);
		// }
		// if (sb != null && sb.length() > 0) {
		// res = sb.toString();
		// }
		return sb.toString();
	}

	public static String removePrefix(String aString, String prefix, String separator) {

		String res = aString;

		if (res.indexOf(prefix + separator) > -1) {

			res = res.replaceFirst(prefix + separator, "");

			logger.debug(String.format("Rimosso %s da: %s", prefix + separator, prefix));
		}

		return res;
	}

	public static String addPrefix(String aString, String prefix, String separator) {

		String res = aString;

		if (!(res.indexOf(prefix + separator) > -1)) {

			// res = res.replaceFirst(prefix + separator, "");
			res = prefix + separator + aString;

			logger.debug(String.format("Aggiunto %sa: %s", prefix + separator, prefix));
		}

		return res;
	}

	/**
	 * Speparated by , and with no start and end []
	 * 
	 * @param indirizziAttivi lista indirizzi email
	 * @return indirizzi concatenati
	 */
	@Deprecated
	public static String toCommaSeparatedString(List<String> indirizziAttivi) {
		String indirizziSelezionati = ListUtils.toCommaSepared(indirizziAttivi);
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(indirizziSelezionati)) {
			indirizziSelezionati = indirizziSelezionati.substring(1, indirizziSelezionati.length() - 1);
		}

		return indirizziSelezionati;
	}

	/**
	 * Separated by white spaces
	 * 
	 * @param indirizziAttivi lista indirizzi email
	 * @return indirizzi concatenati
	 */
	public static String toSeparatedString(List<String> indirizziAttivi) {
		String indirizziSelezionati = ListUtils.toCommaSepared(indirizziAttivi);
		indirizziSelezionati = indirizziSelezionati.replaceAll(", ", " ");
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(indirizziSelezionati)) {
			indirizziSelezionati = indirizziSelezionati.substring(1, indirizziSelezionati.length() - 1);
		}

		return indirizziSelezionati;
	}

	/**
	 * Empty string in case of null
	 * 
	 * @param aString
	 * @return
	 */
	public static String notNull(String aString) {
		return notNull(aString, "");
	}

	/**
	 * Never null, instead defautlValue
	 * 
	 * @param aString
	 * @param defaultValue
	 * @return
	 */
	public static String notNull(String aString, String defaultValue) {
		String res = defaultValue;

		if (aString != null) {
			res = aString;
		}

		return res;
	}

	public static boolean difference(String string1, String string2) {
		boolean res = false;

		if (string1 != null && string2 == null) {
			res = true;
		} else if (string1 == null && string2 != null) {
			res = true;
		} else if (string1 != null && string2 != null) {
			if (!string1.equalsIgnoreCase(string2)) {
				res = true;
			}
		}

		return res;
	}

	public static String removeDoubleSpace(String aString) {
		String tempo = aString;

		try {
			tempo = aString.replaceAll("\\s+", " ");
		} catch (Exception ex) {
			logger.error("removeDoubleSpace", ex);
		}

		return tempo;
	}

	public static boolean equalsToAny(String aString, String... strings) {
		boolean found = false;
		for (String a : strings) {
			found = aString.equals(a);
			if (found) break;
		}
		return found;
	}
	
	/*
	 * from StringFunc
	 */
	
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
	
	/**
	 * 
	 * @param str
	 * @param expr
	 * @return
	 */
	public static boolean like(String str, String expr) {
		if (str == null || expr == null) 
			return false;
		
	    expr = expr.toLowerCase(); // ignoring locale for now
	    expr = expr.replace(".", "\\."); // "\\" is escaped to "\" (thanks, Alan M)
	    // ... escape any other potentially problematic characters here
	    expr = expr.replace("?", ".");
	    expr = expr.replace("%", ".*");
	    str = str.toLowerCase();
	    return str.matches(expr);
	}	
}
