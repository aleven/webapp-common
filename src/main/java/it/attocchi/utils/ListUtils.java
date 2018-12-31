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
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * <p>ListUtils class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class ListUtils {

	/**
	 * Verifica se una lista e' nulla o vuota
	 *
	 * @param aList a {@link java.util.List} object.
	 * @param <T>  generic
	 * @return a boolean.
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
	 * @param aList a {@link java.util.Collection} object.
	 * @param <T> generic
	 * @return a boolean.
	 */
	public static <T> boolean isNotEmpty(Collection<T> aList) {
		return aList != null && aList.size() > 0;
		// return !isEmpty(aList);
	}

	/**
	 * <p>toList.</p>
	 *
	 * @param anArray an array of {@link java.lang.String} objects.
	 * @return a {@link java.util.List} object.
	 */
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

	/**
	 * <p>toArray.</p>
	 *
	 * @param aList a {@link java.util.List} object.
	 * @return an array of {@link java.lang.String} objects.
	 */
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
	 * @param aStringWithValues a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 */
	public static List<String> fromCommaSepared(String aStringWithValues) {
		List<String> res = new ArrayList<String>();

		if (StringUtils.isNotBlank(aStringWithValues)) {
			if (aStringWithValues.startsWith("[")) {
				aStringWithValues = aStringWithValues.substring(1);
			}

			if (aStringWithValues.endsWith("]")) {
				aStringWithValues = aStringWithValues.substring(0, aStringWithValues.length() - 1);
			}

			res = new ArrayList<String>(Arrays.asList(aStringWithValues.split("\\s*,\\s*"))); // Arrays.asList(aStringWithValues.split("\\s*,\\s*"));
		}

		return res;
	}

	/**
	 * <p>fromCommaSeparedSet.</p>
	 *
	 * @param aStringWithValues a {@link java.lang.String} object.
	 * @return a {@link java.util.Set} object.
	 */
	public static Set<String> fromCommaSeparedSet(String aStringWithValues) {
		Set<String> res = new HashSet<String>();

		if (StringUtils.isNotBlank(aStringWithValues)) {
			if (aStringWithValues.startsWith("[")) {
				aStringWithValues = aStringWithValues.substring(1);
			}

			if (aStringWithValues.endsWith("]")) {
				aStringWithValues = aStringWithValues.substring(0, aStringWithValues.length() - 1);
			}

			res = new HashSet<String>(Arrays.asList(aStringWithValues.split("\\s*,\\s*"))); // Arrays.asList(aStringWithValues.split("\\s*,\\s*"));
		}

		return res;
	}

	/**
	 * <p>fromCommaSeparedLong.</p>
	 *
	 * @param aStringWithValues a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 */
	public static List<Long> fromCommaSeparedLong(String aStringWithValues) {
		List<Long> res = new ArrayList<Long>();

		List<String> valori = fromCommaSepared(aStringWithValues);
		if (ListUtils.isNotEmpty(valori)) {
			for (String valore : valori) {
				res = newIfNull(res);
				if (StringUtils.isNotBlank(valore))
					res.add(Long.valueOf(valore));
			}
		}

		return res;
	}

	/**
	 * <p>toCommaSepared.</p>
	 *
	 * @param aListOfString a {@link java.util.Collection} object.
	 * @param <T>           a T object.
	 * @return [string, string, ... ]
	 */
	public static <T> String toCommaSepared(Collection<T> aListOfString) {
		String res = "";
		if (isNotEmpty(aListOfString)) {
			res = aListOfString.toString();
		}
		return res;
	}

	/**
	 * <p>toCommaSeparedNoBracket.</p>
	 *
	 * @param aListOfString a {@link java.util.Collection} object.
	 * @param <T>           a T object.
	 * @return a {@link java.lang.String} object.
	 */
	public static <T> String toCommaSeparedNoBracket(Collection<T> aListOfString) {
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
	 * @param aList    a List where add the object (can be empty)
	 * @param anObject an object to add to to the list
	 * @param <T>      a T object.
	 */
	public static <T> void add(List<T> aList, T anObject) {
		if (aList == null) {
			aList = new ArrayList<T>();
		}
		aList.add(anObject);
	}

	/**
	 * <p>find.</p>
	 *
	 * @param aList a {@link java.util.List} object.
	 * @param key   a T object.
	 * @param c     a {@link java.util.Comparator} object.
	 * @param <T>   a T object.
	 * @return a T object.
	 */
	public static <T> T find(List<T> aList, T key, Comparator<T> c) {
		T res = null;

		Collections.sort(aList, c);
		int index = Collections.binarySearch(aList, key, c);

		if (index >= 0) {
			res = aList.get(index);
		}

		return res;
	}

	/**
	 * <p>find.</p>
	 *
	 * @param aList a {@link java.util.List} object.
	 * @param key   a T object.
	 * @param <T>   a T object.
	 * @return a T object.
	 */
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
	 * @param aList a {@link java.util.List} object.
	 * @param id    a long.
	 * @param <T>   IEntityWithIdLong
	 * @return a T object.
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

	/**
	 * <p>findByIdsLong.</p>
	 *
	 * @param aList a {@link java.util.List} object.
	 * @param ids   a {@link java.lang.String} object.
	 * @param <T>   a T object.
	 * @return a {@link java.util.List} object.
	 */
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

	/**
	 * <p>findByIdsLong.</p>
	 *
	 * @param aMap a {@link java.util.Map} object.
	 * @param ids  a {@link java.lang.String} object.
	 * @param <T>  a T object.
	 * @return a {@link java.util.List} object.
	 */
	public static <T extends IEntityWithIdLong> List<T> findByIdsLong(Map<Long, T> aMap, String ids) {
		List<T> res = new ArrayList<T>();
		if (StringUtils.isNotBlank(ids)) {
			List<Long> idsLong = fromCommaSeparedLong(ids);
			for (Long id : idsLong) {
				if (aMap.containsKey(id)) {
					res.add(aMap.get(id));
				}
			}
		}
		return res;
	}

	/**
	 * <p>fromValues.</p>
	 *
	 * @param values a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 */
	public static List<String> fromValues(String... values) {
		List<String> res = new ArrayList<String>();

		for (int i = 0; i < values.length; i++) {
			res.add(values[i]);
		}

		return res;
	}

	/**
	 * <p>newIfNull.</p>
	 *
	 * @param listaTags a {@link java.util.List} object.
	 * @param <T>       a T object.
	 * @return a {@link java.util.List} object.
	 */
	public static <T> List<T> newIfNull(List<T> listaTags) {
		List<T> res = listaTags;
		if (res == null)
			res = new ArrayList<T>();

		return res;
	}

	/**
	 * <p>newIfNullSet.</p>
	 *
	 * @param listaTags a {@link java.util.Set} object.
	 * @param <T>       a T object.
	 * @return a {@link java.util.Set} object.
	 */
	public static <T> Set<T> newIfNullSet(Set<T> listaTags) {
		Set<T> res = listaTags;
		if (res == null)
			res = new HashSet<T>();

		return res;
	}

	/**
	 * <p>clear.</p>
	 *
	 * @param aList a {@link java.util.List} object.
	 * @param <T>   a T object.
	 */
	public static <T> void clear(List<T> aList) {
		if (aList != null) {
			aList.clear();
		}
	}

	/**
	 * <p>addToListOfString.</p>
	 *
	 * @param aCommaSeparatedList a {@link java.lang.String} object.
	 * @param newValue            a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String addToListOfString(String aCommaSeparatedList, String newValue) {
		List<String> list = null;
		list = ListUtils.newIfNull(list);

		if (StringUtils.isNotBlank(aCommaSeparatedList)) {
			list = fromCommaSepared(aCommaSeparatedList);
			list = ListUtils.newIfNull(list);
		}
		list.add(newValue);

		return toCommaSepared(list);
	}

	/**
	 * <p>addToSetOfString.</p>
	 *
	 * @param aCommaSeparatedList a {@link java.lang.String} object.
	 * @param newValue            a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String addToSetOfString(String aCommaSeparatedList, String newValue) {
		Set<String> list = null;
		list = ListUtils.newIfNullSet(list);

		if (StringUtils.isNotBlank(aCommaSeparatedList)) {
			list = fromCommaSeparedSet(aCommaSeparatedList);
			list = ListUtils.newIfNullSet(list);
		}
		list.add(newValue);

		return toCommaSepared(list);
	}

	/**
	 * <p>addToListOfLong.</p>
	 *
	 * @param aCommaSeparatedList a {@link java.lang.String} object.
	 * @param newValue            a long.
	 * @return a {@link java.lang.String} object.
	 */
	public static String addToListOfLong(String aCommaSeparatedList, long newValue) {
		List<Long> list = null;
		list = ListUtils.newIfNull(list);

		if (StringUtils.isNotBlank(aCommaSeparatedList)) {
			list = fromCommaSeparedLong(aCommaSeparatedList);
			list = ListUtils.newIfNull(list);
		}
		list.add(newValue);

		return toCommaSepared(list);
	}

	/**
	 * se la lista non è vuota ritorna il primo elemento
	 *
	 * @param aList una lista
	 * @param <T>   a T object.
	 * @return il primo elemento della lista
	 */
	public static <T> T fistItem(List<T> aList) {
		T res = null;
		if (isNotEmpty(aList))
			res = aList.get(0);
		return res;
	}

}
