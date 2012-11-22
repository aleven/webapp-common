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
		List<String> res = null;

		// if (anArray != null && anArray.length > 0) {
		// res = new ArrayList<String>(anArray.length);
		// for (String s : anArray) {
		// res.add(s);
		// }
		// }

		res = Arrays.asList(anArray);

		return res;
	}

	public static String[] toArray(List<String> aList) {
		String[] res = null;

		if (aList != null) {
			res = aList.toArray(new String[aList.size()]);
			// for (String s : anArray) {
			// res.add(s);
			// }
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
		
		if (aStringWithValues.startsWith("[")) {
			aStringWithValues = aStringWithValues.substring(1);
		}
		
		if (aStringWithValues.endsWith("]")) {
			aStringWithValues = aStringWithValues.substring(0, aStringWithValues.length() - 1);
		}
		
		
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

	public static String toCommaSeparedNoBracket(List<String> aListOfString) {
		String res = "";
		if (!ListUtils.isEmpty(aListOfString)) {
			res = aListOfString.toString();

			res = res.substring(1);
			res = res.substring(0, res.length() - 1);
		}

		return res;
	}

	/**
	 * If @List is Empty inizialize it to a new @ArrayList
	 * 
	 * @param aList
	 *            a List where add the object (can be empty)
	 * @param anObject
	 *            an object to add to to the list
	 */
	public static <T> void add(List<T> aList, T anObject) {
		if (aList == null) {
			aList = new ArrayList<T>();
		}
		aList.add(anObject);
	}
}
