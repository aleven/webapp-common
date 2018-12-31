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

package it.attocchi.jpa2;

/**
 * Usage in web.xml:
 * <pre>
 * {@code
 *   <listener>
 *   	<listener-class>it.attocchi.jpa2.JPASessionListener</listener-class>
 *   </listener>
 * }
 * </pre>
 * @author Mirco Attocchi
 * @version $Id: $Id
 */
public interface IJpaListernes {
	
	/** Constant <code>DEFAULT_PU="DEFAULT_PU"</code> */
	public static final String DEFAULT_PU = "DEFAULT_PU";
	/** Constant <code>WEB_XML_INITPARAMETER_NAME="PersistenceUnitName"</code> */
	public static final String WEB_XML_INITPARAMETER_NAME = "PersistenceUnitName";
	
	/** Constant <code>APPLICATION_EMF="APPLICATION_EMF"</code> */
	public static final String APPLICATION_EMF = "APPLICATION_EMF";
	/** Constant <code>SESSION_EMF="SESSION_EMF"</code> */
	public static final String SESSION_EMF = "SESSION_EMF";
	
}
