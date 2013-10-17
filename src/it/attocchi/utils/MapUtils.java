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

import java.util.Map;

public class MapUtils {

	// public static <T, K extends Number> void addToMap(Map<T, K> aMap, T aKey, K aValue) {
	public static <T> void addToMap(Map<T, Float> aMap, T aKey, Float aValue) {
		if (aMap != null) {

			if (!aMap.containsKey(aKey)) {
				aMap.put(aKey, aValue);
			} else {
				Float old = aMap.get(aKey);
				old = old + aValue;
				
				aMap.put(aKey, old);
			}

		}
	}
	
	public static <T> void addToMapExistKey(Map<T, Float> aMap, T aKey, Float aValue) {
		if (aMap != null) {

			if (!aMap.containsKey(aKey)) {
				// aMap.put(aKey, aValue);
			} else {
				Float old = aMap.get(aKey);
				old = old + aValue;
				
				aMap.put(aKey, old);
			}

		}
	}	

}
