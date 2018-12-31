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

import it.webappcommon.lib.json.JsonTransient;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

/**
 * <p>JSONUtils class.</p>
 *
 * @author Mirco
 * @version $Id: $Id
 */
public class JSONUtils {

	/**
	 * <p>fromList.</p>
	 *
	 * @param aList a {@link java.util.List} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String fromList(List aList) {
		String res = null;

		// JSONArray jsonArray = new JSONArray(aList);
		JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(aList);
		res = jsonArray.toString();

		return res;

	}

	/**
	 * <p>fromListExcludeJsonTransient.</p>
	 *
	 * @param aList a {@link java.util.List} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String fromListExcludeJsonTransient(List aList) {
		String res = null;

		// JSONArray jsonArray = new JSONArray(aList);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.addIgnoreFieldAnnotation(JsonTransient.class);

		JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(aList, jsonConfig);
		res = jsonArray.toString();

		return res;

	}

	/**
	 * <p>emptyArray.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public static String emptyArray() {
		return "[]"; // Inizializzazione di Base ArrayJson
	}
}
