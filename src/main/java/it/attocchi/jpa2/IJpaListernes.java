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
 * 
 *   <listener>
 *   	<listener-class>it.attocchi.jpa2.JPASessionListener</listener-class>
 *   </listener>
 * 
 * 
 * 
 * @author Mirco Attocchi
 *
 */
public interface IJpaListernes {
	
	public static final String DEFAULT_PU = "DEFAULT_PU";
	public static final String WEB_XML_INITPARAMETER_NAME = "PersistenceUnitName";
	
	public static final String APPLICATION_EMF = "APPLICATION_EMF";
	public static final String SESSION_EMF = "SESSION_EMF";
	
}
