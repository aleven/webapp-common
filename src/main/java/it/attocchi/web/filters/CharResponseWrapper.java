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

package it.attocchi.web.filters;

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.log4j.Logger;

/**
 * <p>CharResponseWrapper class.</p>
 * @author mirco
 * @version $Id: $Id
 */
public class CharResponseWrapper extends HttpServletResponseWrapper {

	protected final Logger logger = Logger.getLogger(this.getClass().getName());

	private CharArrayWriter output;

	/**
	 * <p>Constructor for CharResponseWrapper.</p>
	 *
	 * @param response a {@link javax.servlet.http.HttpServletResponse} object.
	 */
	public CharResponseWrapper(HttpServletResponse response) {
		super(response);
		this.output = new CharArrayWriter();
	}

	/**
	 * <p>toString.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String toString() {
		return output.toString();
	}

	/**
	 * <p>getWriter.</p>
	 *
	 * @return a {@link java.io.PrintWriter} object.
	 */
	public PrintWriter getWriter() {
		return new PrintWriter(output);
	}
}
