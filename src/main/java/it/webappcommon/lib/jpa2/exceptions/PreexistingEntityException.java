/*
    Copyright (c) 2007,2014 Mirco Attocchi
	
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

package it.webappcommon.lib.jpa2.exceptions;

/**
 * <p>PreexistingEntityException class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class PreexistingEntityException extends Exception {
	/**
	 * <p>Constructor for PreexistingEntityException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param cause a {@link java.lang.Throwable} object.
	 */
	public PreexistingEntityException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * <p>Constructor for PreexistingEntityException.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public PreexistingEntityException(String message) {
		super(message);
	}
}
