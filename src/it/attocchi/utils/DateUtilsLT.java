package it.attocchi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

public class DateUtilsLT {

	protected static final Logger logger = Logger.getLogger(DateUtils.class.getName());

	public static final String FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public final static String FORMAT_ISO = "yyyyMMdd";

	public final static String FORMAT_ISO_HHMM = "yyyyMMdd HH:mm";
	public final static String FORMAT_ISO_HHMM_SEPARATOR = "yyyy-MM-dd HH:mm";

	public static final String FORMAT_HHmmssSS = "HHmmssSS";
	public static final String FORMAT_HHmmssSS_SEPARATOR = "HH:mm:ss:SS";

	public static final String FORMAT_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

	public static final String FORMAT_DATE_IT = "dd/MM/yyyy";
	public static final String FORMAT_DATE_TIME_IT = "dd/MM/yyyy HH:mm";
	public static final String FORMAT_DATE_IT_COMPACT = "dd/MM/yy";
	public static final String FORMAT_TIME_IT = "HH:mm";

	public static Date Now() {
		return new Date();
	}

	public static String Now(String pattern) {
		return new SimpleDateFormat(pattern).format(Now());
	}

	public static String getNowFormatISO() {
		return new SimpleDateFormat(FORMAT_ISO).format(Now());
	}

	public static String getYear(Date aDate) {
		return new SimpleDateFormat("yyyy").format(aDate);
	}

	public static String getMonth(Date aDate) {
		return new SimpleDateFormat("MM").format(aDate);
	}

	public static String getDay(Date aDate) {
		return new SimpleDateFormat("dd").format(aDate);
	}

	public static Date addDays(Date aDate, int days) {

		Calendar cal = new GregorianCalendar();
		cal.setTime(aDate);

		cal.add(Calendar.DATE, days);

		return cal.getTime();
	}

	public static List<Date> dateBetweens(Date start, Date end) {
		List<Date> res = new ArrayList<Date>();

		Calendar startCal = getCalendar(start);
		while (startCal.getTime().before(end)) {
			Date resultado = startCal.getTime();
			res.add(resultado);

			startCal.add(Calendar.DATE, 1);
		}

		return res;
	}

	/**
	 * Aggiunge 12:00 per evitare problemi con CET CEST. Una data 20120309
	 * potrebbe diventare 2012 03 08 perche viene valorizzata con ora 00:00 e
	 * quando la si formatta nel locale Italiano Java la fa diventare il giorno
	 * precedente passando da CET a CEST
	 * 
	 * @param aIsoString
	 * @return
	 * @throws ParseException
	 */
	public static Date parseISO(String aIsoString) throws ParseException {
		return new SimpleDateFormat(DateUtils.FORMAT_ISO_HHMM).parse(aIsoString + " 12:00");
	}

	/**
	 * 2013-12-13
	 * 
	 * @param aIsoString
	 * @return
	 * @throws ParseException
	 */
	public static Date parseISOSeparator(String aIsoString) throws ParseException {
		return new SimpleDateFormat(DateUtils.FORMAT_ISO_HHMM_SEPARATOR).parse(aIsoString + " 12:00");
	}

	/**
	 * Calcolate duration starting from a string like 01:30 (1 hour and 30
	 * minutes)
	 * 
	 * @param HHmm
	 * @return value of duration as numeric value
	 */
	public static float calculateDuration(String HHmm) {

		String[] data = HHmm.split(":");
		int hours = Integer.parseInt(data[0]);
		int minutes = Integer.parseInt(data[1]);

		float res = hours * 60 * 60 * 1000 + minutes * 60 * 1000;

		res = res / (1000 * 60 * 60);

		return res;
	}

	public static Calendar getCalendar(Date dt) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(dt);
		return cal;
	}

	public static boolean isWorkDay(Date data) {

		int theDay = getCalendar(data).get(Calendar.DAY_OF_WEEK);

		return theDay != Calendar.SUNDAY && theDay != Calendar.SATURDAY;

	}

	/**
	 * Week number of the date. From the help this may be different by Calendar
	 * Locale
	 * 
	 * @param aDate
	 * @return
	 */
	public static int getWeekOfTheYear(Date aDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(aDate);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * Create a Date from specified data
	 * 
	 * @param year
	 * @param month
	 *            DAY_OF_MONTH
	 * @param day
	 * @param hours
	 *            HOUR_OF_DAY
	 * @param minutes
	 * @return
	 */
	public static Date getDate(int year, int month, int day, int hours, int minutes) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, hours);
		c.set(Calendar.MINUTE, minutes);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}

	/**
	 * Set time of a Date
	 * 
	 * @param aDate
	 * @param hours
	 * @param minutes
	 * @return
	 */
	public static Date setTime(Date aDate, int hours, int minutes) {
		Calendar c = Calendar.getInstance();
		c.setTime(aDate);
		c.set(Calendar.HOUR_OF_DAY, hours);
		c.set(Calendar.MINUTE, minutes);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}

	/**
	 * Verify if a Date is Between other dates, looking only for the date, not
	 * for the time
	 * 
	 * @param aMoment
	 * @param from
	 * @param to
	 * @return
	 */
	public static boolean isBetween(Date aMoment, Date from, Date to) {

		if (aMoment == null)
			return false;

		if (from == null)
			from = aMoment;

		if (to == null)
			to = aMoment;

		Calendar a = Calendar.getInstance();
		a.setTime(from);
		Calendar b = Calendar.getInstance();
		b.setTime(to);

		a.set(Calendar.HOUR_OF_DAY, 0);
		a.set(Calendar.MINUTE, 0);
		a.set(Calendar.SECOND, 1);

		b.set(Calendar.HOUR_OF_DAY, 23);
		b.set(Calendar.MINUTE, 59);
		b.set(Calendar.SECOND, 59);

		return aMoment.getTime() >= a.getTime().getTime() && aMoment.getTime() <= b.getTime().getTime();
	}

}
