package it.attocchi.test;

import it.attocchi.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class TestDateUtils {

	static Date inizio,
		fine = null;
	static Calendar cal1,
		cal2;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		cal1 = Calendar.getInstance();
		cal2 = Calendar.getInstance();
		inizio = cal1.getTime();
		fine = cal2.getTime();
		test();

		cal1.add(Calendar.DATE, -1);
		inizio = cal1.getTime();
		fine = cal2.getTime();
		test();

	}

	private static void test() {

		System.out.println(inizio);
		System.out.println(fine);

		System.out.println(DateUtils.differenzaInGiorni(fine, inizio));
		System.out.println(DateUtils.differenzaInGiorni(inizio, fine));
		System.out.println(DateUtils.DiffDays(inizio, fine));
		System.out.println(DateUtils.DiffDays(fine, inizio));
		System.out.println(DateUtils.getDaysBetween(cal2, cal1));
		System.out.println(DateUtils.getDaysBetween(cal1, cal2));
	}
}
