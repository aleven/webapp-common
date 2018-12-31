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

import java.io.Serializable;

/**
 * <p>XMLUtils class.</p>
 *
 * @author Mirco
 * @param <T> classe che estende Serializable
 * @version $Id: $Id
 */
public class XMLUtils<T extends Serializable> {

	/**
	 * <p>encodeXML.</p>
	 *
	 * @param aSerializableObject a T object.
	 * @return a {@link java.lang.String} object.
	 */
	public String encodeXML(T aSerializableObject) {
		String res = null;

		return res;
	}
	
	/**
	 * <p>decodeXML.</p>
	 *
	 * @param xmlSource a {@link java.lang.String} object.
	 * @return a T object.
	 */
	public T decodeXML(String xmlSource) {
		T res = null;

		return res;
	}	

}
