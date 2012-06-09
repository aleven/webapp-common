package it.espressoft.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUtils {

	/**
	 * Verifica se una lista e' nulla o vuota
	 * 
	 * @param aList
	 * @return
	 */
	public static boolean isEmpty(List aList) {
		boolean res = true;

		if (aList != null && aList.size() > 0) {
			res = false;
		}

		return res;
	}

	/**
	 * Verifica se una lista non e' nulla e vuota
	 * 
	 * @param aList
	 * @return
	 */
	public static boolean isNotEmpty(List aList) {
		return !isEmpty(aList);
	}

	public static List<String> toList(String[] anArray) {
		List res = null;

		if (anArray != null && anArray.length > 0) {
			res = new ArrayList<String>(anArray.length);
			for (String s : anArray) {
				res.add(s);
			}
		}

		return res;
	}

	/**
	 * Gestisce anche gli elementi con spazi dopo la virgola
	 * 
	 * @param aStringWithValues
	 * @return
	 */
	public static List<String> fromCommaSepared(String aStringWithValues) {
		return Arrays.asList(aStringWithValues.split("\\s*,\\s*"));
	}

	/**
	 * 
	 * @param aListOfString
	 * @return [string, string, ... ]
	 */
	public static String toCommaSepared(List<String> aListOfString) {
		String res = "";
		if (!ListUtils.isEmpty(aListOfString)) {
			res = aListOfString.toString();
		}
		return res;
	}
}
