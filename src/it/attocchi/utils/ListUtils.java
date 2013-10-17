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

import it.attocchi.jpa2.entities.IEntityWithIdLong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ListUtils {

	/**
	 * Verifica se una lista e' nulla o vuota
	 * 
	 * @param aList
	 * @return
	 */
	public static <T> boolean isEmpty(List<T> aList) {
		// boolean res = true;
		//
		// if (aList != null && aList.size() > 0) {
		// res = false;
		// }
		//
		// return res;
		return !isNotEmpty(aList);
	}

	/**
	 * Verifica se una lista non e' nulla e vuota
	 * 
	 * @param aList
	 * @return
	 */
	public static <T> boolean isNotEmpty(List<T> aList) {
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

	public static List<Long> fromCommaSeparedLong(String aStringWithValues) {
		List<Long> res = null;

		List<String> valori = fromCommaSepared(aStringWithValues);
		if (ListUtils.isNotEmpty(valori)) {
			for (String valore : valori) {
				res = newIfNull(res);
				res.add(Long.valueOf(valore));
			}
		}

		return res;
	}

	/**
	 * 
	 * @param aListOfString
	 * @return [string, string, ... ]
	 */
	public static <T> String toCommaSepared(List<T> aListOfString) {
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

	/**
	 * Verificare che sia il modo migliore per cercare per id
	 * 
	 * @param aList
	 * @param id
	 * @return
	 */
	public static <T extends IEntityWithIdLong> T findByIdLong(List<T> aList, long id) {
		T res = null;

		for (T item : aList) {
			if (item.getId() == id) {
				res = item;
				break;
			}
		}

		// Comparator<ISearchableByIdLong> comparator = new
		// Comparator<ISearchableByIdLong>() {
		// public int compare(ISearchableByIdLong o1, ISearchableByIdLong o2) {
		// // if (o1.getId().equals(o2.getId())) {
		// // return o1.b.compareTo(o2.b);
		// // }
		// // return o1.a.compareTo(o2.a);
		// return new Long(o1.getId()).compareTo(o2.getId());
		// }
		// };
		// Collections.sort(aList, comparator);
		// int i = Collections.binarySearch(aList, id, comparator);

		return res;
	}

	public static <T extends IEntityWithIdLong> List<T> findByIdsLong(List<T> aList, String ids) {
		List<T> res = new ArrayList<T>();

		if (StringUtils.isNotBlank(ids)) {
			for (T item : aList) {
				List<Long> idsLong = fromCommaSeparedLong(ids);
				for (Long id : idsLong) {
					if (item.getId() == id) {
						res.add(item);
						// break;
					}
				}
			}
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

	public static <T> void clear(List<T> aList) {
		if (aList != null) {
			aList.clear();
		}
	}

	/**
	 * 
	 * @param aCommaSeparatedList
	 * @param newValue
	 * @return
	 */
	public static String addToListOfString(String aCommaSeparatedList, String newValue) {
		List<String> list = null;
		ListUtils.newIfNull(list);

		if (StringUtils.isNotBlank(aCommaSeparatedList)) {
			list = fromCommaSepared(aCommaSeparatedList);
			ListUtils.newIfNull(list);
			list.add(newValue);
		}

		return toCommaSepared(list);
	}

	public static String addToListOfLong(String aCommaSeparatedList, long newValue) {
		List<Long> list = null;
		ListUtils.newIfNull(list);

		if (StringUtils.isNotBlank(aCommaSeparatedList)) {
			list = fromCommaSeparedLong(aCommaSeparatedList);
			ListUtils.newIfNull(list);
			list.add(newValue);
		}

		return toCommaSepared(list);
	}

}
