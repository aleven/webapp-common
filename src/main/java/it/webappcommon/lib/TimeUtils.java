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

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class TimeUtils {
	
	public static float timeToFloat(String aTimeDuration) {
		float oreLavorate = 0f;
		if (aTimeDuration != null && aTimeDuration.length() > 0) {
			String[] membri = aTimeDuration.split(":");
			if (membri.length > 1) {
				oreLavorate = Float.parseFloat(membri[0]);
				oreLavorate = oreLavorate + (Float.parseFloat(membri[1]) / 60);
			} else {
				oreLavorate = Float.parseFloat(membri[0]);
			}
		}
		return oreLavorate;
	}

	public static String floatToTime(float aTimeDuration) {

		long iPart = (long) aTimeDuration;
		double fPart = aTimeDuration - iPart;

		String hours = String.valueOf(iPart);
		String minutes = String.valueOf((long) (fPart * 60));

		if (hours.length() == 1) {
			hours = "0" + hours;
		}

		if (minutes.length() == 1) {
			minutes = "0" + minutes;
		}

		return hours + ":" + minutes;
	}
	
	/**
	 * decodifica il testo nella forma
	 * <ul>
	 * <li>Nm min minuto minuti</li>
	 * <li>Ns sec secondo secondi</li>
	 * <li>Ng gg giorno giorni</li>
	 * <li>Nh ora ore</li>
	 * <li>Nms milli millisecondi</li>
	 * </ul>
	 * dove N è un numero intero
	 * @param value testo da decodificare
	 * @return secondi
	 */
	public static int decode(String value) {
		int res = 0;
		int data = 0;
		int toMS = 1; // 1000
		if (StringUtils.isNotBlank(value)) {
			if (value.endsWith("s")) {
				data = Integer.parseInt(value.substring(0, value.indexOf("s")));
				res = data * toMS;
			} else if (value.endsWith("sec")) {
				data = Integer.parseInt(value.substring(0, value.indexOf("sec")));
				res = data * toMS;
			} else if (value.endsWith("secondo")) {
				data = Integer.parseInt(value.substring(0, value.indexOf("secondo")));
				res = data * toMS;
			} else if (value.endsWith("secondi")) {
				data = Integer.parseInt(value.substring(0, value.indexOf("secondi")));
				res = data * toMS;
				
			} else if (value.endsWith("m")) {
				data = Integer.parseInt(value.substring(0, value.indexOf("m")));
				res = data * 60 * toMS;
			} else if (value.endsWith("min")) {
				data = Integer.parseInt(value.substring(0, value.indexOf("min")));
				res = data * 60 * toMS;
			} else if (value.endsWith("minuto")) {
				data = Integer.parseInt(value.substring(0, value.indexOf("minuto")));
				res = data * 60 * toMS;
			} else if (value.endsWith("minuti")) {
				data = Integer.parseInt(value.substring(0, value.indexOf("minuti")));
				res = data * 60 * toMS;
				
			} else if (value.endsWith("h")) {
				data = Integer.parseInt(value.substring(0, value.indexOf("h")));
				res = data * 60 * 60 * toMS;
			} else if (value.endsWith("ora")) {
				data = Integer.parseInt(value.substring(0, value.indexOf("ora")));
				res = data * 60 * 60 * toMS;
			} else if (value.endsWith("ore")) {
				data = Integer.parseInt(value.substring(0, value.indexOf("ore")));
				res = data * 60 *60 * toMS;
				
			} else if (value.endsWith("ms")) {
				data = Integer.parseInt(value.substring(0, value.indexOf("ms")));
				res = data;
			} else if (value.endsWith("milli")) {
				data = Integer.parseInt(value.substring(0, value.indexOf("milli")));
				res = data;
			} else if (value.endsWith("millisecondi")) {
				data = Integer.parseInt(value.substring(0, value.indexOf("millisecondi")));
				res = data;
				
			} else if (value.endsWith("g")) {
				data = Integer.parseInt(value.substring(0, value.indexOf("g")));
				res = data * 24 * 60 * 60 * toMS;
			} else if (value.endsWith("gg")) {
				data = Integer.parseInt(value.substring(0, value.indexOf("gg")));
				res = data * 24 * 60 * 60 * toMS;
			} else if (value.endsWith("giorno")) {
				data = Integer.parseInt(value.substring(0, value.indexOf("giorno")));
				res = data * 24 * 60 * 60 * toMS;
			} else if (value.endsWith("giorni")) {
				data = Integer.parseInt(value.substring(0, value.indexOf("giorni")));
				res = data * 24 * 60 * 60 * toMS;
				
			} else {
				data = Integer.parseInt(value);
				res = data;
			}
		}
		return res;
	}
	
	/**
	 * 
	 * @param aDate
	 * @param value
	 * @return
	 */
	public static Date aggiungi(Date aDate, String value) {
		Calendar c = Calendar.getInstance();
		c.setTime(aDate);
		
		int ms = decode(value);
		
		c.add(Calendar.SECOND, ms);
		
		return c.getTime();
	}
}
