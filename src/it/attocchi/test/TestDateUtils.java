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

package it.attocchi.test;

import it.attocchi.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

public class TestDateUtils {

	static Date inizio,
		fine = null;
	static Calendar cal1,
		cal2;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		 testDifferenza();

//		testSuddivisione();
	}

	private static void testDifferenza() {

		cal1 = Calendar.getInstance();
		cal2 = Calendar.getInstance();

		cal1.set(2013, 11 - 1, 11, 16, 00);
		cal2.set(2013, 11 - 1, 19, 18, 00);

		inizio = cal1.getTime();
		fine = cal2.getTime();

		System.out.println(inizio);
		System.out.println(fine);

		System.out.println(DateUtils.differenzaInGiorni(fine, inizio));
		System.out.println(DateUtils.differenzaInGiorni(inizio, fine));
		System.out.println(DateUtils.DiffDays(inizio, fine));
		System.out.println(DateUtils.DiffDays(fine, inizio));
		System.out.println(DateUtils.getDaysBetween(cal2, cal1));
		System.out.println(DateUtils.getDaysBetween(cal1, cal2));

	}

	private static void testSuddivisione() {

		DateTime now = new DateTime();
		List<Interval> list = DateUtils.splitDuration(now, now.plusDays(10), 3, 60 * 60 * 24 * 7 * 1000);

		for (Interval i : list) {
			System.out.println(i.getStart() + " - " + i.getEnd() + " - " + i.toDurationMillis());
		}

	}
}
