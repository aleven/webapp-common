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

import java.util.List;

/**
 * <p>Abstract ListEach class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public abstract class ListEach<E> {

	List<E> aList;
	//
	// public ListEach(List<E> aList) {
	// super();
	// this.aList = aList;
	// }

	/**
	 * <p>Constructor for ListEach.</p>
	 *
	 * @param aList a {@link java.util.List} object.
	 */
	public ListEach(List<E> aList) {
		this.aList = aList;
	}
	
	/**
	 * <p>forEach.</p>
	 */
	public void forEach() {
		if (ListUtils.isNotEmpty(aList)) {
			for (E item : aList) {
				eachDo(item);
			}
		}
	}
	
	/**
	 * <p>eachDo.</p>
	 *
	 * @param item a E object.
	 */
	protected abstract void eachDo(E item);
	
//	public static <T> ListEach forEach2(List<T> aList) {
//		if (ListUtils.isNotEmpty(aList)) {
//			
//
//			for (T item : aList) {
//				eachDo(item);
//			}
//		}
//	}
	
	// abstract static <T> void eachDo2(T item);
}
