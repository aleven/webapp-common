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

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

public class JQueryUtils {

	public static String listToJSArrayItems(List<String> list) throws Exception {

		StringBuilder retVal = null;
		try {
			retVal = new StringBuilder();
			retVal.append("[");
			for (String s : list) {
				if (s == null) {
					s = "";
				}
				retVal.append("\"");
				retVal.append(s);
				retVal.append("\"");
				retVal.append(",");

			}
			// Rimuovo la virgola dopo l'ultimo elemento
			if (retVal.toString().endsWith(",")) {
				retVal.deleteCharAt(retVal.length() - 1);
			}
			retVal.append("]");
		} catch (Exception e) {
			throw e;
		}
		return retVal.toString();

	}

	/**
	 * 
	 * @param list
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public static String listToJSArrayItems(List list, String item) throws Exception {
		StringBuilder retVal = null;
		try {
			retVal = new StringBuilder();
			retVal.append("[");
			for (Object obj : list) {

				PropertyDescriptor pdLabel = new PropertyDescriptor(item, obj.getClass());
				Method methodLabel = pdLabel.getReadMethod();
				Object methodLabelRes = methodLabel.invoke(obj);

				if (methodLabelRes == null) {
					methodLabelRes = "";
				}
				retVal.append("\"");
				retVal.append(methodLabelRes.toString());
				retVal.append("\"");
				retVal.append(",");

				pdLabel = null;
				methodLabel = null;
				methodLabelRes = null;
			}
			// Rimuovo la virgola dopo l'ultimo elemento
			if (retVal.toString().endsWith(",")) {
				retVal.deleteCharAt(retVal.length() - 1);
			}
			retVal.append("]");
		} catch (Exception e) {
			throw e;
		}
		return retVal.toString();
	}
}
