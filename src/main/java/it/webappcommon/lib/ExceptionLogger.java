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

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

/**
 * <p>ExceptionLogger class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class ExceptionLogger {
	/**
	 * <p>logException.</p>
	 *
	 * @param logger a {@link org.apache.log4j.Logger} object.
	 * @param ex a {@link java.lang.Throwable} object.
	 */
	public static void logException(Logger logger, Throwable ex) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		String stacktrace = sw.toString();
		logger.error(stacktrace);
	}
	
	/**
	 * <p>logExceptionWithCause.</p>
	 *
	 * @param logger a {@link org.apache.log4j.Logger} object.
	 * @param ex a {@link java.lang.Throwable} object.
	 */
	public static void logExceptionWithCause(Logger logger, Throwable ex) {		
		logger.error(ex);
		Throwable cause = ex.getCause();
		if (cause != null) {
			logger.error(cause);
		}
	}	
}
