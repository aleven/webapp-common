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

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * <p>Crono class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class Crono {

	private static Map<String, Date> starts = new HashMap<String, Date>();
	private static Map<String, Date> stops = new HashMap<String, Date>();

	/**
	 * <p>start.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public static void start(String name) {
		// if (starts == null)
		// starts = new HashMap<String, Date>();

		starts.put(name, new Date());
	}

	private static void stop(String name) {
		// if (stops == null)
		// stops = new HashMap<String, Date>();

		stops.put(name, new Date());

	}

	/**
	 * <p>stopAndLog.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String stopAndLog(String name) {
		String res = "Crono \"" + name + "\" not available.";

		stop(name);

		if (starts.containsKey(name)) {
			Date start = starts.get(name);
			Date stop = stops.get(name);

			long diff = stop.getTime() - start.getTime();

			if (diff < 1000) {
				res = "Crono " + name + ": " + diff + "ms";
			} else {
				double seconds = diff / 1000d;
				res = "Crono " + name + ": " + new DecimalFormat("#.##").format(seconds) + "s";
			}

			// res = "Crono " + name + ": " + diff + "ms";
		}

		return res;
	}

	/**
	 * <p>stopAndLogDebug.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param logger a {@link org.apache.log4j.Logger} object.
	 */
	public static void stopAndLogDebug(String name, Logger logger) {
		logger.debug(stopAndLog(name));
	}
	
	/**
	 * <p>stopAndLogInfo.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param logger a {@link org.apache.log4j.Logger} object.
	 */
	public static void stopAndLogInfo(String name, Logger logger) {
		logger.info(stopAndLog(name));
	}	

	/**
	 * <p>stopAndSysOut.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public static void stopAndSysOut(String name) {
		System.out.println(stopAndLog(name));
	}
}
