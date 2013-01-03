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
