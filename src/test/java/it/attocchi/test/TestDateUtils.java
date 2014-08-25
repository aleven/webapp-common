package it.attocchi.test;

import it.attocchi.utils.DateUtilsLT;
import it.webappcommon.lib.DateUtils;

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

	public static void main(String[] args) {
		// testDifferenza();

		// testSuddivisione();
		
		testWorkingDays();
	}

	private static void testDifferenza() {

		cal1 = Calendar.getInstance();
		cal2 = Calendar.getInstance();

		// cal1.set(2014, 06 - 1, 03, 16, 00);
		// cal2.set(2014, 07 - 1, 03, 16, 00);
		cal2.add(Calendar.DAY_OF_MONTH, 10);
		// cal2.set(Calendar.HOUR_OF_DAY, 2);

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

		System.out.println(DateUtils.differenzaInSettimane(inizio, fine));
		System.out.println(DateUtils.differenzaInMesi(inizio, fine));
	}

	private static void testSuddivisione() {

		DateTime now = new DateTime();
		List<Interval> list = DateUtils.splitDuration(now, now.plusDays(10), 3, 60 * 60 * 24 * 7 * 1000);

		for (Interval i : list) {
			System.out.println(i.getStart() + " - " + i.getEnd() + " - " + i.toDurationMillis());
		}

	}

	private static void isBetween() {
		System.out.println(DateUtilsLT.isBetween(new Date(), new Date(), new Date()));
		System.out.println(DateUtilsLT.isBetween(new Date(), DateUtilsLT.addDays(new Date(), -1), new Date()));
		System.out.println(DateUtilsLT.isBetween(new Date(), DateUtilsLT.addDays(new Date(), -10), DateUtilsLT.addDays(new Date(), 10)));
		System.out.println(DateUtilsLT.isBetween(new Date(), DateUtilsLT.addDays(new Date(), 10), DateUtilsLT.addDays(new Date(), 50)));
	}
	
	private static void testWorkingDays() {
		
		Date start = new Date();
		Date end = DateUtilsLT.addDays(new Date(), 8);
//		start = DateUtilsLT.setTime(start, 12, 0);
//		end = DateUtilsLT.setTime(end, 12, 0);
		
		System.out.println(start + "," + end);
		System.out.println(DateUtilsLT.getWorkingDaysBetweenTwoDates(start, end));
		
		
	}
}
