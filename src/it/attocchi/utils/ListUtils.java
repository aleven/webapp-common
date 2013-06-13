package it.attocchi.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang3.StringUtils;

public class ListUtils {

	/**
	 * Verifica se una lista e' nulla o vuota
	 * 
	 * @param aList
	 * @return
	 */
	public static boolean isEmpty(List aList) {
//		boolean res = true;
//
//		if (aList != null && aList.size() > 0) {
//			res = false;
//		}
//
//		return res;
		return !isNotEmpty(aList);
	}

	/**
	 * Verifica se una lista non e' nulla e vuota
	 * 
	 * @param aList
	 * @return
	 */
	public static boolean isNotEmpty(List aList) {
		return aList != null && aList.size() > 0;
		// return !isEmpty(aList);
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
		List<String> res = null;

		if (aStringWithValues != null) {
			if (aStringWithValues.startsWith("[")) {
				aStringWithValues = aStringWithValues.substring(1);
			}

			if (aStringWithValues.endsWith("]")) {
				aStringWithValues = aStringWithValues.substring(0, aStringWithValues.length() - 1);
			}

			res = Arrays.asList(aStringWithValues.split("\\s*,\\s*"));
		}

		return res;
	}

	/**
	 * 
	 * @param aListOfString
	 * @return [string, string, ... ]
	 */
	public static String toCommaSepared(List<String> aListOfString) {
		String res = "";
		if (isNotEmpty(aListOfString)) {
			res = aListOfString.toString();
		}
		return res;
	}

	public static String toCommaSeparedNoBracket(List<String> aListOfString) {
		String res = "";

		res = toCommaSepared(aListOfString);

		if (StringUtils.isNotBlank(res)) {
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

	public static <T> T find(List<T> aList, T key, Comparator<T> c) {
		T res = null;

		Collections.sort(aList, c);
		int index = Collections.binarySearch(aList, key, c);

		if (index >= 0) {
			res = aList.get(index);
		}

		return res;
	}

	public static <T extends Comparable<T>> T find(List<T> aList, T key) {
		T res = null;

		Collections.sort(aList);
		int index = Collections.binarySearch(aList, key);

		if (index >= 0) {
			res = aList.get(index);
		}

		return res;
	}

	public static List<String> fromValues(String... values) {
		List<String> res = new ArrayList<String>();

		for (int i = 0; i < values.length; i++) {
			res.add(values[i]);
		}

		return res;
	}

	public static <T> List<T> newIfNull(List<T> listaTags) {
		List<T> res = listaTags;
		if (res == null)
			res = new ArrayList<T>();
		
		return res;
	}
	
	public static void clear(List aList) {
		if (aList != null) {
			aList.clear();
		}
	}

}
