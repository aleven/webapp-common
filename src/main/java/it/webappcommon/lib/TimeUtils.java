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
}
