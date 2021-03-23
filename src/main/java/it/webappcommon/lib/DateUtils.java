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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

//import org.joda.time.DateTime;
//import org.joda.time.DateTimeZone;
//import org.joda.time.Days;
//import org.joda.time.Duration;
//import org.joda.time.Interval;
//import org.joda.time.LocalDate;
//import org.joda.time.LocalDateTime;
//import org.joda.time.Months;
//import org.joda.time.Weeks;
//import org.joda.time.format.DateTimeFormat;
//import org.joda.time.format.DateTimeFormatter;

/**
 *
 * DateUtils that not require Joda
 *
 * @author Mirco
 * @version $Id: $Id
 */
public class DateUtils {

	/** Constant <code>logger</code> */
	protected static final Logger logger = Logger.getLogger(DateUtils.class.getName());

	/**
	 * yyyy-MM-dd'T'HH:mm:ss'Z'
	 */
	public static final String FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	/**
	 * yyyyMMdd
	 */
	public final static String FORMAT_ISO = "yyyyMMdd";
	/**
	 * yyyy-MM-dd
	 */
	public final static String FORMAT_ISO_SEPARATOR = "yyyy-MM-dd";
	/**
	 * yyyyMMdd HH:mm
	 */
	public final static String FORMAT_ISO_HHmm = "yyyyMMdd HH:mm";
	/**
	 * yyyy-MM-dd HH:mm
	 */
	public final static String FORMAT_ISO_HHmm_SEPARATOR = "yyyy-MM-dd HH:mm";
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public final static String FORMAT_ISO_HHmmss_SEPARATOR = "yyyy-MM-dd HH:mm:ss";
	/**
	 * HHmmssSS
	 */
	public static final String FORMAT_HHmmssSS = "HHmmssSS";
	/**
	 * HH:mm:ss:SS
	 */
	public static final String FORMAT_HHmmssSS_SEPARATOR = "HH:mm:ss:SS";

	/**
	 * yyyyMMddHHmmssSSS
	 */
	public static final String FORMAT_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	/**
	 * dd/MM/yyyy
	 */
	public static final String FORMAT_DATE_IT = "dd/MM/yyyy";
	/**
	 * dd-MM-yyyy
	 */
	public static final String FORMAT_DATE_IT_SEPARATOR_MINUS = "dd-MM-yyyy";
	/**
	 * dd/MM/yyyy HH:mm
	 */
	public static final String FORMAT_DATE_TIME_IT = "dd/MM/yyyy HH:mm";
	/**
	 * dd/MM/yy
	 */
	public static final String FORMAT_DATE_IT_COMPACT = "dd/MM/yy";
	/**
	 * HH:mm
	 */
	public static final String FORMAT_TIME_IT = "HH:mm";
	/**
	 * yyyyMMdd.HHmmss
	 */
	public static final String FORMAT_yyyyMMdd_dot_HHmmss = "yyyyMMdd.HHmmss";

	/**
	 * <p>Now.</p>
	 *
	 * @return a {@link java.util.Date} object.
	 */
	public static Date Now() {
		return new Date();
	}

	/**
	 * <p>Now.</p>
	 *
	 * @param pattern a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String Now(String pattern) {
		return new SimpleDateFormat(pattern).format(Now());
	}

	/**
	 * <p>NowFormatISO.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public static String NowFormatISO() {
		return new SimpleDateFormat(FORMAT_ISO).format(Now());
	}

	/**
	 * <p>NowYear2.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public static String NowYear2() {
		return new SimpleDateFormat("yy").format(Now());
	}

	/**
	 * <p>NowYear4.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public static String NowYear4() {
		return new SimpleDateFormat("yyyy").format(Now());
	}

	/**
	 * <p>getYear.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getYear(Date aDate) {
		return new SimpleDateFormat("yyyy").format(aDate);
	}

	/**
	 * <p>getYearAsInt.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a int.
	 */
	public static int getYearAsInt(Date aDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(aDate);
		return c.get(Calendar.YEAR);
	}

	/**
	 * Ritorna il giorno del mese della data specificata
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a int.
	 */
	public static int getGiorno(Date aDate) {

		Calendar tempCal = new GregorianCalendar();
		tempCal.setTime(aDate);

		return tempCal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * <p>getNowFormatTZGMT.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public static String getNowFormatTZGMT() {
		return getFormatTZGMT(new GregorianCalendar().getTime());
	}

	/**
	 * <p>getFormatTZGMT.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getFormatTZGMT(Date aDate) {

		SimpleDateFormat dateFormatGmt = new SimpleDateFormat(FORMAT_ISO_8601);
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

		return dateFormatGmt.format(aDate);
	}

	/**
	 * Data nel formato MM
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getMonth(Date aDate) {
		return new SimpleDateFormat("MM").format(aDate);
	}

	/**
	 * Data nel formato dd
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getDay(Date aDate) {
		return new SimpleDateFormat("dd").format(aDate);
	}

	/**
	 * <p>addHours.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @param hours a int.
	 * @return a {@link java.util.Date} object.
	 */
	public static Date addHours(Date aDate, int hours) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(aDate);
		cal.add(Calendar.HOUR_OF_DAY, hours);
		return cal.getTime();
	}

	/**
	 * <p>addDays.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @param days a int.
	 * @return a {@link java.util.Date} object.
	 */
	public static Date addDays(Date aDate, int days) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(aDate);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	/**
	 * <p>addMonths.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @param months a int.
	 * @return a {@link java.util.Date} object.
	 */
	public static Date addMonths(Date aDate, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(aDate);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**
	 * <p>dateBetweens.</p>
	 *
	 * @param start a {@link java.util.Date} object.
	 * @param end a {@link java.util.Date} object.
	 * @return a {@link java.util.List} object.
	 */
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
	 * @param aIsoString a {@link java.lang.String} object.
	 * @return a {@link java.util.Date} object.
	 * @throws java.text.ParseException if any.
	 */
	public static Date parseISO(String aIsoString) throws ParseException {
		return new SimpleDateFormat(FORMAT_ISO_HHmm).parse(aIsoString + " 12:00");
	}

	/**
	 * 2013-12-13
	 *
	 * @param aIsoString a {@link java.lang.String} object.
	 * @return a {@link java.util.Date} object.
	 * @throws java.text.ParseException if any.
	 */
	public static Date parseISOSeparator(String aIsoString) throws ParseException {
		return new SimpleDateFormat(FORMAT_ISO_HHmm_SEPARATOR).parse(aIsoString + " 12:00");
	}

	/**
	 * Calcolate duration starting from a string like 01:30 (1 hour and 30
	 * minutes)
	 *
	 * @param HHmm a {@link java.lang.String} object.
	 * @return a float.
	 */
	public static float calculateDuration(String HHmm) {

		String[] data = HHmm.split(":");
		int hours = Integer.parseInt(data[0]);
		int minutes = Integer.parseInt(data[1]);

		float res = hours * 60 * 60 * 1000 + minutes * 60 * 1000;

		res = res / (1000 * 60 * 60);

		return res;
	}

	/**
	 * <p>getCalendar.</p>
	 *
	 * @param dt a {@link java.util.Date} object.
	 * @return a {@link java.util.Calendar} object.
	 */
	public static Calendar getCalendar(Date dt) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(dt);
		return cal;
	}

	/**
	 * <p>isWorkDay.</p>
	 *
	 * @param data a {@link java.util.Date} object.
	 * @return a boolean.
	 */
	public static boolean isWorkDay(Date data) {

		int theDay = getCalendar(data).get(Calendar.DAY_OF_WEEK);

		return theDay != Calendar.SUNDAY && theDay != Calendar.SATURDAY;

	}

	/**
	 * Week number of the date. From the help this may be different by Calendar
	 * Locale
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a int.
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
	 *            YEAR
	 * @param month
	 *            MONTH
	 * @param day
	 *            DAY_OF_MONTH
	 * @param hours
	 *            HOUR_OF_DAY
	 * @param minutes
	 *            MINUTE
	 * @return a date
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
	 * crea una data dai dati specificati (imposta orario alle 00:00)
	 *
	 * @param year
	 *            YEAR
	 * @param month
	 *            MONTH
	 * @param day
	 *            DAY_OF_MONTH
	 * @return a date at 00:00
	 */
	public static Date getDate(int year, int month, int day) {
		return getDate(year, month, day, 0, 0);
	}

	/**
	 * crea una data dai dati specificati (imposta orario alle 23:59)
	 *
	 * @param year anno
	 * @param month mese
	 * @param day giorno
	 * @return a date at 23:59
	 */
	public static Date getDateEnd(int year, int month, int day) {
		return getDate(year, month, day, 23, 59);
	}

	/**
	 * Set time of a Date
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @param hours a int.
	 * @param minutes a int.
	 * @return a {@link java.util.Date} object.
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
	 * @param aMoment a {@link java.util.Date} object.
	 * @param from a {@link java.util.Date} object.
	 * @param to a {@link java.util.Date} object.
	 * @return a boolean.
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

	/**
	 * <p>getAnno.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a int.
	 */
	public static int getAnno(Date aDate) {
		Calendar tempCal = new GregorianCalendar();
		tempCal.setTime(aDate);
		return tempCal.get(Calendar.YEAR);
	}

	/**
	 * <p>getMonthZeroBased.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a int.
	 */
	public static int getMonthZeroBased(Date aDate) {
		return getMeseZeroBased(aDate);
	}

	/**
	 * Ritorna il mese della data specificata non 0 based
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a int.
	 */
	public static int getMese(Date aDate) {
		return getMeseZeroBased(aDate) + 1;
	}

	/**
	 * <p>getMeseZeroBased.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a int.
	 */
	public static int getMeseZeroBased(Date aDate) {
		Calendar tempCal = new GregorianCalendar();
		tempCal.setTime(aDate);
		return tempCal.get(Calendar.MONTH);
	}

	/**
	 * <p>getLastDayOfMontZeroBased.</p>
	 *
	 * @param monthZeroBased
	 *            numero del mese zero-based
	 * @param year a int.
	 * @return a int.
	 * @throws java.lang.Exception if any.
	 */
	public static int getLastDayOfMontZeroBased(int monthZeroBased, int year) throws Exception {
		return getLastDayOfMont(monthZeroBased + 1, year);
	}

	/**
	 * <p>getLastDayOfMont.</p>
	 *
	 * @param monthNonZeroBased
	 *            Numero del mese non zero-based
	 * @param year a int.
	 * @return a int.
	 * @throws java.lang.Exception if any.
	 */
	public static int getLastDayOfMont(int monthNonZeroBased, int year) throws Exception {
		int res = 0;

		/*
		 * monthNonZeroBased rappresenta nativamente il mese successivo
		 */

		if (monthNonZeroBased >= 1 && monthNonZeroBased < 12) {

			Calendar cal = new GregorianCalendar();
			cal.set(year, monthNonZeroBased, 1);

			cal.add(Calendar.DATE, -1);

			res = cal.get(Calendar.DATE);

		} else if (monthNonZeroBased == 12) {

			Calendar cal = new GregorianCalendar();
			cal.set(getAnno(cal.getTime()) + 1, 1, 1);

			cal.add(Calendar.DATE, -1);

			res = cal.get(Calendar.DATE);
		} else {
			throw new Exception("Mese non valido");
		}

		return res;
	}

	/**
	 * <p>getLastDateOfMonth.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a {@link java.util.Date} object.
	 * @throws java.lang.Exception if any.
	 */
	public static Date getLastDateOfMonth(Date aDate) throws Exception {

		Calendar cal = new GregorianCalendar(getAnno(aDate), getMeseZeroBased(aDate), getLastDayOfMontZeroBased(getMeseZeroBased(aDate), getAnno(aDate)));
		return cal.getTime();
	}

	/**
	 * Original code from source:
	 * http://stackoverflow.com/questions/4600034/calculate
	 * -number-of-weekdays-between-two-dates-in-java
	 *
	 * @param startDate a {@link java.util.Date} object.
	 * @param endDate a {@link java.util.Date} object.
	 * @return a int.
	 */
	public static int getWorkingDaysBetweenTwoDates(Date startDate, Date endDate) {

		startDate = setTime(startDate, 12, 0);
		endDate = setTime(endDate, 12, 0);

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);

		int workDays = 0;

		// Return 0 if start and end are the same
		if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
			return 0;
		}

		if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
			startCal.setTime(endDate);
			endCal.setTime(startDate);
		}

		do {
			// excluding start date
			startCal.add(Calendar.DAY_OF_MONTH, 1);
			if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				++workDays;
			}
		} while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); // excluding
																			// end
																			// date

		return workDays;
	}

	/**
	 * <p>getDate.</p>
	 *
	 * @param date a {@link java.util.Date} object.
	 * @param inputTime a {@link java.lang.String} object.
	 * @return a {@link java.util.Date} object.
	 * @throws java.text.ParseException if any.
	 */
	public static Date getDate(Date date, String inputTime) throws ParseException {
		DateFormat format = new SimpleDateFormat("HH:mm");
		Date time = format.parse(inputTime);
		return getDate(date, time);
	}

	/**
	 * <p>getDate.</p>
	 *
	 * @param date a {@link java.util.Date} object.
	 * @param time a {@link java.util.Date} object.
	 * @return a {@link java.util.Date} object.
	 */
	public static Date getDate(Date date, Date time) {
		Calendar calendarA = Calendar.getInstance();
		calendarA.setTime(date);

		Calendar calendarB = Calendar.getInstance();
		calendarB.setTime(time);

		calendarA.set(Calendar.HOUR_OF_DAY, calendarB.get(Calendar.HOUR_OF_DAY));
		calendarA.set(Calendar.MINUTE, calendarB.get(Calendar.MINUTE));
		calendarA.set(Calendar.SECOND, calendarB.get(Calendar.SECOND));
		calendarA.set(Calendar.MILLISECOND, calendarB.get(Calendar.MILLISECOND));

		Date result = calendarA.getTime();

		return result;
	}

	/**
	 * <p>format.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @param format a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String format(Date aDate, String format) {
		return new SimpleDateFormat(format).format(aDate);
	}

	/**
	 * <p>format.</p>
	 *
	 * @param aDate a {@link java.util.Calendar} object.
	 * @param format a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String format(Calendar aDate, String format) {
		return format(aDate.getTime(), format);
	}

	/**
	 * <p>parseTime.</p>
	 *
	 * @param timeString a {@link java.lang.String} object.
	 * @return a {@link java.util.Date} object.
	 * @throws java.text.ParseException if any.
	 */
	public static Date parseTime(String timeString) throws ParseException {
		return new SimpleDateFormat(FORMAT_TIME_IT).parse(timeString);
	}

	/**
	 * verifica se una data esiste
	 *
	 * @param anno a int.
	 * @param mese a int.
	 * @param giorno a int.
	 * @return a boolean.
	 */
	public static boolean exists(int anno, int mese, int giorno) {
		boolean res = false;

//		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
////		format.parse("2010-02-31"); //=> Ok, rolls to "Wed Mar 03 00:00:00 PST 2010".
//		format.setLenient(false);
////		format.parse("2010-02-31"); //=> Throws ParseException "Unparseable date".


		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, anno);
		c.set(Calendar.MONTH, mese);
		c.set(Calendar.DAY_OF_MONTH, giorno);

		int annoTest = c.get(Calendar.YEAR);
		int meseTest = c.get(Calendar.MONTH);
		int giornoTest = c.get(Calendar.DAY_OF_MONTH);

		res = (anno == annoTest && mese == meseTest && giorno == giornoTest);

		return res;
	}

	/**
	 * cerca una data valida se quella specificata non è valida. cercando all'indietro nel tempo
	 *
	 * @param anno YEAR
	 * @param mese MONTH
	 * @param giorno DAY_OF_MONTH
	 * @return la prima data valida trovata se quella specificata non è valida.
	 */
	public static Date searchValidBefore(int anno, int mese, int giorno) {
		Date res = null;
		if (!exists(anno, mese, giorno)) {
			// cerco scorrendo i giorni all'indietro
			for (int i = giorno - 1; i > 0; i--) {
				if (exists(anno, mese, i)) {
					res = getDateEnd(anno, mese, i);
					break;
				}
			}
		} else {
			res = getDateEnd(anno, mese, giorno);
		}
		return res;
	}

	// protected static final Logger logger =
	// Logger.getLogger(DateUtils.class.getName());

	// public static final String FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	// public final static String FORMAT_ISO = "yyyyMMdd";
	//
	// public final static String FORMAT_ISO_HHMM = "yyyyMMdd HH:mm";
	//
	// public static final String FORMAT_HHmmssSS = "HH:mm:ss:SS";

	// /**
	// *
	// * @param aDate
	// * @return
	// */
	// public static int getAnno(Date aDate) {
	//
	// Calendar tempCal = new GregorianCalendar();
	// tempCal.setTime(aDate);
	//
	// return tempCal.get(Calendar.YEAR);
	// }

//	/**
//	 * Ritorna il mese della data specificata non 0 based
//	 *
//	 * @param aDate
//	 * @return
//	 */
//	public static int getMese(Date aDate) {
//		return getMeseZeroBased(aDate) + 1;
//	}

	// public static int getMeseZeroBased(Date aDate) {
	//
	// Calendar tempCal = new GregorianCalendar();
	// tempCal.setTime(aDate);
	//
	// return tempCal.get(Calendar.MONTH);
	// }

//	/**
//	 * Ritorna il giorno del mese della data specificata
//	 *
//	 * @param aDate
//	 * @return
//	 */
//	public static int getGiorno(Date aDate) {
//
//		Calendar tempCal = new GregorianCalendar();
//		tempCal.setTime(aDate);
//
//		return tempCal.get(Calendar.DAY_OF_MONTH);
//	}
//
//	public static String getNowFormatTZGMT() {
//		return getFormatTZGMT(new GregorianCalendar().getTime());
//	}
//
//	public static String getFormatTZGMT(Date aDate) {
//
//		SimpleDateFormat dateFormatGmt = new SimpleDateFormat(FORMAT_ISO_8601);
//		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
//
//		return dateFormatGmt.format(aDate);
//	}

	// public static String getNowFormatISO() {
	// return new SimpleDateFormat(FORMAT_ISO).format(new Date());
	// }

	// public static Date Now() {
	// return new Date();
	// }

	/**
	 * Ritorna l'ora della Data nel formato HH:mm
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String getOra(Date aDate) {
		return new SimpleDateFormat("HH:mm").format(aDate);
	}

//	public static int differenzaInGiorni(Date start, Date end) {
//		int res = 0;
//
//		DateTime startDate = new DateTime(start);
//		DateTime endDate = new DateTime(end);
//
//		Days giorni = Days.daysBetween(startDate, endDate);
//		res = giorni.getDays();
//
//		return res;
//	}
//
//	public static int differenzaInSettimane(Date start, Date end) {
//		int res = 0;
//
//		DateTime startDate = new DateTime(start);
//		DateTime endDate = new DateTime(end);
//
//		Weeks settimane = Weeks.weeksBetween(startDate, endDate);
//		res = settimane.getWeeks();
//
//		return res;
//	}
//
//	public static int differenzaInMesi(Date start, Date end) {
//		int res = 0;
//
//		DateTime startDate = new DateTime(start);
//		DateTime endDate = new DateTime(end);
//
//		Months mesi = Months.monthsBetween(startDate, endDate);
//		res = mesi.getMonths();
//
//		return res;
//	}
//
//	public static List<Date> calcolaDateIntermedie(Date start, Date end) {
//		List<Date> res = new ArrayList<Date>();
//
//		DateTime startDate = new DateTime(start);
//		DateTime endDate = new DateTime(end);
//
//		if (differenzaInGiorni(start, end) > 0) {
//
//			res.add(startDate.toDate());
//			startDate = startDate.plusDays(1);
//
//			while (differenzaInGiorni(startDate.toDate(), end) != 0) {
//				res.add(startDate.toDate());
//				startDate = startDate.plusDays(1);
//			}
//
//			res.add(startDate.toDate());
//
//		} else {
//			res.add(start);
//		}
//
//		return res;
//	}

	/**
	 * <p>verificaSovrapposizione.</p>
	 *
	 * @param dateStartApp1 a {@link java.util.Date} object.
	 * @param dateEndApp1 a {@link java.util.Date} object.
	 * @param dateStartApp2 a {@link java.util.Date} object.
	 * @param dateEndApp2 a {@link java.util.Date} object.
	 * @param unEstremoPuoEssereUguale a boolean.
	 * @return a boolean.
	 */
	public static boolean verificaSovrapposizione(Date dateStartApp1, Date dateEndApp1, Date dateStartApp2, Date dateEndApp2, boolean unEstremoPuoEssereUguale) {
		return verificaSovrapposizione(dateStartApp1.getTime(), dateEndApp1.getTime(), dateStartApp2.getTime(), dateEndApp2.getTime(), unEstremoPuoEssereUguale);
	}

	/**
	 * <p>verificaSovrapposizione.</p>
	 *
	 * @param dateStartApp1 a long.
	 * @param dateEndApp1 a long.
	 * @param dateStartApp2 a long.
	 * @param dateEndApp2 a long.
	 * @param unEstremoPuoEssereUguale a boolean.
	 * @return a boolean.
	 */
	public static boolean verificaSovrapposizione(long dateStartApp1, long dateEndApp1, long dateStartApp2, long dateEndApp2, boolean unEstremoPuoEssereUguale) {
		boolean result = true;

		if (unEstremoPuoEssereUguale) {
			if (dateStartApp1 < dateStartApp2 && dateEndApp1 <= dateStartApp2) {
				result = false;
			} else if (dateStartApp1 >= dateEndApp2 && dateEndApp1 > dateEndApp2) {
				result = false;
			}
		} else {
			if (dateStartApp1 < dateStartApp2 && dateEndApp1 < dateStartApp2) {
				result = false;
			} else if (dateStartApp1 > dateEndApp2 && dateEndApp1 > dateEndApp2) {
				result = false;
			}
		}

		return result;
	}

	/*
	 * Aggiunte Gianluca
	 */
	/**
	 * Ritorna se il giorno cade in un fine settimana cioe' sabato o domenica
	 *
	 * @param dt a {@link java.util.Date} object.
	 * @return a boolean.
	 */
	public static boolean isWeekend(Date dt) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(dt);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 7=Sabato, 1=Domenica
		return (dayOfWeek == 7 || dayOfWeek == 1) ? true : false;
	}

	/**
	 * <p>
	 * Checks if a date is today.
	 * </p>
	 *
	 * @param date
	 *            the date, not altered, not null.
	 * @return true if the date is today.
	 * @throws java.lang.IllegalArgumentException
	 *             if the date is <code>null</code>
	 */
	public static boolean isToday(Date date) {
		return isSameDay(date, Calendar.getInstance().getTime());
	}

	/**
	 * <p>
	 * Checks if a calendar date is today.
	 * </p>
	 *
	 * @param cal
	 *            the calendar, not altered, not null
	 * @return true if cal date is today
	 * @throws java.lang.IllegalArgumentException
	 *             if the calendar is <code>null</code>
	 */
	public static boolean isToday(Calendar cal) {
		return isSameDay(cal, Calendar.getInstance());
	}

	/**
	 * <p>
	 * Checks if two calendars represent the same day ignoring time.
	 * </p>
	 *
	 * @param cal1
	 *            the first calendar, not altered, not null
	 * @param cal2
	 *            the second calendar, not altered, not null
	 * @return true if they represent the same day
	 * @throws java.lang.IllegalArgumentException
	 *             if either calendar is <code>null</code>
	 */
	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2
				.get(Calendar.DAY_OF_YEAR));
	}

	/**
	 * <p>
	 * Checks if two dates are on the same day ignoring time.
	 * </p>
	 *
	 * @param date1
	 *            the first date, not altered, not null
	 * @param date2
	 *            the second date, not altered, not null
	 * @return true if they represent the same day
	 * @throws java.lang.IllegalArgumentException
	 *             if either date is <code>null</code>
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		// if (date1 == null || date2 == null) {
		// throw new IllegalArgumentException("The dates must not be null");
		// }
		if (date1 == null || date2 == null) {
			return false;
		}

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameDay(cal1, cal2);
	}

	/**
	 * <p>isSameMonth.</p>
	 *
	 * @param date1 a {@link java.util.Date} object.
	 * @param date2 a {@link java.util.Date} object.
	 * @return a boolean.
	 */
	public static boolean isSameMonth(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameDay(cal1, cal2);
	}

	/**
	 * <p>isNotSameDay.</p>
	 *
	 * @param date1 a {@link java.util.Date} object.
	 * @param date2 a {@link java.util.Date} object.
	 * @return a boolean.
	 */
	public static boolean isNotSameDay(Date date1, Date date2) {
		return !isSameDay(date1, date2);
	}

	/**
	 * <p>isSameMonth.</p>
	 *
	 * @param cal1 a {@link java.util.Calendar} object.
	 * @param cal2 a {@link java.util.Calendar} object.
	 * @return a boolean.
	 */
	public static boolean isSameMonth(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH));
	}

	/**
	 * Settimana dell'anno
	 *
	 * @param dt a {@link java.util.Date} object.
	 * @return a int.
	 */
	public static int WeekOfTheYear(Date dt) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(dt);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * Mette a zero la componente tempo della data es. 23/04/2010 24:25:01
	 * diventa 23/04/2010 00:00:00
	 *
	 * @param dt a {@link java.util.Date} object.
	 * @return a {@link java.util.Date} object.
	 */
	public static Date getDateNoTime(Date dt) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(dt);

		Calendar newcal = new GregorianCalendar();
		newcal.set(cal.get(cal.YEAR), cal.get(cal.MONTH), cal.get(cal.DAY_OF_MONTH), 0, 0, 0);
		newcal.set(Calendar.MILLISECOND, 0);
		return newcal.getTime();
	}

	/**
	 * ritorna il giorno della settimana
	 *
	 * @param dt data
	 * @return 1 e' domenica, .. 7 sabato
	 */
	public static int DayOfTheWeek(Date dt) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(dt);
		return cal.get(Calendar.DAY_OF_WEEK); // 7=Sabato, 1=Domenica
		// int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 7=Sabato,
		// 1=Domenica
		// //conversione
		// switch (dayOfWeek) {
		// case 1: return 7;
		// case 2: return 1;
		// case 3: return 2;
		// case 4: return 3;
		// case 5: return 4;
		// case 6: return 5;
		// case 7: return 6;
		// default: return 0;
		// }
	}

	/**
	 * Ritona il prefisso del giorno della settimana indicato
	 *
	 * @param dt data
	 * @return prefisso del giorno
	 */
	public static String DayPrefix(Date dt) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(dt);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 7=Sabato, 1=Domenica

		switch (dayOfWeek) {
		case 1:
			return "Dom";
		case 2:
			return "Lun";
		case 3:
			return "Mar";
		case 4:
			return "Mer";
		case 5:
			return "Gio";
		case 6:
			return "Ven";
		case 7:
			return "Sab";
		default:
			return "";
		}
	}

	/**
	 * Ritorna la data formattata come richiesto
	 *
	 * @param dt
	 *            Data da formattare
	 * @param formatString
	 *            es. "dd-MM-yyyy" oppure "dd-MM-yyyy HH:mm"
	 * @return data formattata
	 */
	public static String getFormat(Date dt, String formatString) {
		if (dt == null)
			return "";
		DateFormat formatter = new SimpleDateFormat(formatString);
		return formatter.format(dt);
	}

	/**
	 * Ritorna la differenza in giorni tra 2 date
	 *
	 * @param startDate a {@link java.util.Date} object.
	 * @param endDate a {@link java.util.Date} object.
	 * @return a int.
	 */
	public static int DiffDays(Date startDate, Date endDate) {
		return (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
	}

	/**
	 * Calculates the number of days between two calendar days in a manner which
	 * is independent of the Calendar type used.
	 *
	 * @param d1
	 *            The first date.
	 * @param d2
	 *            The second date.
	 * @return The number of days between the two dates. Zero is returned if the
	 *         dates are the same, one if the dates are adjacent, etc. The order
	 *         of the dates does not matter, the value returned is always &gt;= 0.
	 *         If Calendar types of d1 and d2 are different, the result may not
	 *         be accurate.
	 */
	public static int getDaysBetween(java.util.Calendar d1, java.util.Calendar d2) {
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(java.util.Calendar.DAY_OF_YEAR) - d1.get(java.util.Calendar.DAY_OF_YEAR);
		int y2 = d2.get(java.util.Calendar.YEAR);
		if (d1.get(java.util.Calendar.YEAR) != y2) {
			d1 = (java.util.Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				d1.add(java.util.Calendar.YEAR, 1);
			} while (d1.get(java.util.Calendar.YEAR) != y2);
		}
		return days;
	} // getDaysBetween()

	// public static int getPreviousQuarter(Date date) {
	// int res = 0;
	//
	// int current = getQuarter(date);
	//
	// if (current > 1) {
	// res = current - 1;
	// } else if (current == 1) {
	// res = 4;
	// }
	// return res;
	// }

	/**
	 * <p>getQuarter.</p>
	 *
	 * @param date a {@link java.util.Date} object.
	 * @return a int.
	 */
	public static int getQuarter(Date date) {
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(date);
		// int year = calendar.get(Calendar.YEAR);
		// int[] months = { 3, 6, 9, 12 };
		// int count = 0;
		// do {
		// calendar.set(year, months[count++], 1);
		// Date tempDt = calendar.getTime();
		// if (date.compareTo(tempDt) < 0) {
		// break;
		// }
		// } while (count < 4);
		// return count;

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH); /* 0 through 11 */
		int quarter = (month / 3) + 1;

		return quarter;
	}

	/**
	 * <p>getQuarterStartDate.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a {@link java.util.Date} object.
	 */
	public static Date getQuarterStartDate(Date aDate) {
		Date res = null;

		int quarter = getQuarter(aDate);

		if (quarter >= 1 && quarter <= 4) {
			Calendar cal = new GregorianCalendar();
			cal.set(getAnno(aDate), 3 * quarter - 3, 1);

			res = cal.getTime();
		}

		return res;
	}

	/**
	 * <p>getPrevQuarterStartDate.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a {@link java.util.Date} object.
	 */
	public static Date getPrevQuarterStartDate(Date aDate) {
		Date res = null;

		int quarter = getQuarter(aDate);

		if (quarter >= 1 && quarter <= 4) {

			int prevQ = quarter - 1;
			int anno = getAnno(aDate);

			if (prevQ == 0) {
				prevQ = 4;
				anno = anno - 1;
			}

			Calendar cal = new GregorianCalendar();
			cal.set(anno, 3 * prevQ - 3, 1);

			res = cal.getTime();
		}

		return res;
	}

	/**
	 * <p>getSixMonthBefore.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a {@link java.util.Date} object.
	 */
	public static Date getSixMonthBefore(Date aDate) {
		Date res = null;

		// int quarter = getQuarter(aDate);
		//
		// if (quarter >= 1 && quarter <= 4) {
		// Calendar cal = new GregorianCalendar();
		// cal.set(getAnno(aDate), 3 * quarter - 3, 1);
		//
		// res = cal.getTime();
		// }

		Calendar move = GregorianCalendar.getInstance();
		move.setTime(aDate);

		Calendar startDate = GregorianCalendar.getInstance();
		startDate.setTime(aDate);

		int differenzaMesi = getMonthsDifference(move, startDate);
		while (differenzaMesi < 5) {
			move.add(Calendar.DATE, -15);

			differenzaMesi = getMonthsDifference(move, startDate);
		}

		return getFirstDateOfMonth(move.getTime());
	}

	/**
	 * <p>getMonthsDifference.</p>
	 *
	 * @param date1 a {@link java.util.Calendar} object.
	 * @param date2 a {@link java.util.Calendar} object.
	 * @return a int.
	 */
	public static int getMonthsDifference(Calendar date1, Calendar date2) {
		int m1 = date1.get(Calendar.YEAR) * 12 + date1.get(Calendar.MONTH);
		int m2 = date2.get(Calendar.YEAR) * 12 + date2.get(Calendar.MONTH);
		return m2 - m1;
	}

	/**
	 * <p>getPrevQuarterEndDate.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a {@link java.util.Date} object.
	 * @throws java.lang.Exception if any.
	 */
	public static Date getPrevQuarterEndDate(Date aDate) throws Exception {
		Date res = null;

		int quarter = getQuarter(aDate);

		if (quarter >= 1 && quarter <= 4) {

			int prevQ = quarter - 1;
			int anno = getAnno(aDate);

			if (prevQ == 0) {
				prevQ = 4;
				anno = anno - 1;
			}

			Calendar cal = new GregorianCalendar();
			cal.set(anno, 3 * prevQ - 1, getLastDayOfMont(3 * prevQ, anno));

			res = cal.getTime();
		}

		return res;
	}

	/**
	 * <p>getFirstDateOfMonth.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a {@link java.util.Date} object.
	 */
	public static Date getFirstDateOfMonth(Date aDate) {
		Calendar cal = new GregorianCalendar(getAnno(aDate), getMeseZeroBased(aDate), 1);
		return cal.getTime();
	}

	// public static Date getLastDateOfMonth(Date aDate) throws Exception {
	//
	// Calendar cal = new GregorianCalendar(getAnno(aDate),
	// getMeseZeroBased(aDate),
	// getLastDayOfMontZeroBased(getMeseZeroBased(aDate), getAnno(aDate)));
	// return cal.getTime();
	// }
	//
	// /**
	// *
	// * @param monthZeroBased
	// * numero del mese zero-based
	// * @return
	// */
	// public static int getLastDayOfMontZeroBased(int monthZeroBased, int year)
	// throws Exception {
	// return getLastDayOfMont(monthZeroBased + 1, year);
	// }
	//
	// /**
	// *
	// * @param monthNonZeroBased
	// * Numero del mese non zero-based
	// * @return
	// */
	// public static int getLastDayOfMont(int monthNonZeroBased, int year)
	// throws Exception {
	// int res = 0;
	//
	// /*
	// * monthNonZeroBased rappresenta nativamente il mese successivo
	// */
	//
	// if (monthNonZeroBased >= 1 && monthNonZeroBased < 12) {
	//
	// Calendar cal = new GregorianCalendar();
	// cal.set(year, monthNonZeroBased, 1);
	//
	// cal.add(Calendar.DATE, -1);
	//
	// res = cal.get(Calendar.DATE);
	//
	// } else if (monthNonZeroBased == 12) {
	//
	// Calendar cal = new GregorianCalendar();
	// cal.set(getAnno(cal.getTime()) + 1, 1, 1);
	//
	// cal.add(Calendar.DATE, -1);
	//
	// res = cal.get(Calendar.DATE);
	// } else {
	// throw new Exception("Mese non valido");
	// }
	//
	// return res;
	// }

	/**
	 * <p>getQuarterEndDate.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a {@link java.util.Date} object.
	 * @throws java.lang.Exception if any.
	 */
	public static Date getQuarterEndDate(Date aDate) throws Exception {
		Date res = null;

		int quarter = getQuarter(aDate);

		if (quarter >= 1 && quarter <= 4) {
			Calendar cal = new GregorianCalendar();
			cal.set(getAnno(aDate), 3 * quarter - 1, getLastDayOfMont(3 * quarter, getAnno(aDate)));

			res = cal.getTime();
		}

		return res;
	}

	/**
	 * Crea una data usando anno mese giorno e i mesi non zero-based
	 *
	 * @param anno a int.
	 * @param mese a int.
	 * @param giorno a int.
	 * @return a {@link java.util.Date} object.
	 */
	public static Date newDate(int anno, int mese, int giorno) {

		Calendar cal = new GregorianCalendar();
		cal.set(anno, mese - 1, giorno);

		return cal.getTime();
	}

	/**
	 * Procedura copiata dal celendario per AppuntamentiDAO
	 *
	 * @param inizio a {@link java.util.Date} object.
	 * @return a {@link java.util.Date} object.
	 */
	public static Date getInizioSettimana(Date inizio) {

		Calendar calWeekInizio = new GregorianCalendar();
		// calWeekFine = new GregorianCalendar();
		calWeekInizio.setTime(inizio);
		int numWeek = 0;

		if (calWeekInizio.get(Calendar.DAY_OF_WEEK) != 1) {
			numWeek = calWeekInizio.get(Calendar.DAY_OF_WEEK) - 2;
		} else {
			numWeek = 6;
		}

		calWeekInizio.set(Calendar.DAY_OF_MONTH, calWeekInizio.get(Calendar.DAY_OF_MONTH) - numWeek);
		// calWeekFine.setTime(calWeekInizio.getTime());
		// calWeekFine.set(Calendar.DAY_OF_MONTH,
		// calWeekInizio.get(Calendar.DAY_OF_MONTH) + 6);

		return calWeekInizio.getTime();
	}

	/**
	 * <p>getFineSettimana.</p>
	 *
	 * @param inizio a {@link java.util.Date} object.
	 * @return a {@link java.util.Date} object.
	 */
	public static Date getFineSettimana(Date inizio) {
		Calendar calWeekFine = new GregorianCalendar();

		Calendar calWeekInizo = new GregorianCalendar();
		calWeekInizo.setTime(getInizioSettimana(inizio));

		calWeekFine.set(Calendar.DAY_OF_MONTH, calWeekInizo.get(Calendar.DAY_OF_MONTH) + 6);

		return calWeekFine.getTime();
	}

//	/**
//	 *
//	 * @param _fromDate
//	 * @param _fromTime
//	 * @param _toDate
//	 * @param _toTime
//	 * @return
//	 */
//	public static Double calculateDuration(Date _fromDate, Date _fromTime, Date _toDate, Date _toTime) {
//		Double duration;
//		Calendar calFromDate = null;
//		Calendar calFromTime = null;
//		Calendar calToDate = null;
//		Calendar calToTime = null;
//		Date fromDateTime = null;
//		Date toDateTime = null;
//
//		try {
//			if ((_fromDate != null) && (_fromTime != null) && (_toDate != null) && (_toTime != null)) {
//
//				logger.debug(String.format("%s=%s", _fromDate, _fromDate.getTime()));
//				logger.debug(String.format("%s=%s", _fromTime, _fromTime.getTime()));
//				logger.debug(String.format("%s=%s", _toDate, _toDate.getTime()));
//				logger.debug(String.format("%s=%s", _toTime, _toTime.getTime()));
//
//				// calFromDate = new GregorianCalendar();
//				// calFromDate.setTime(_fromDate);
//
//				// calFromTime = new GregorianCalendar();
//				// calFromTime.setTime(_fromTime);
//
//				// calToDate = new GregorianCalendar();
//				// calToDate.setTime(_toDate);
//
//				// calToTime = new GregorianCalendar();
//				// calToTime.setTime(_toTime);
//
//				// calFromDate.set(Calendar.HOUR_OF_DAY,
//				// calFromTime.get(Calendar.HOUR_OF_DAY));
//				// calFromDate.set(Calendar.MINUTE,
//				// calFromTime.get(Calendar.MINUTE));
//				// calFromDate.set(Calendar.SECOND,
//				// calFromTime.get(Calendar.SECOND));
//				// calFromDate.set(Calendar.MILLISECOND,
//				// calFromTime.get(Calendar.MILLISECOND));
//				//
//				// calToDate.set(Calendar.HOUR_OF_DAY,
//				// calToTime.get(Calendar.HOUR_OF_DAY));
//				// calToDate.set(Calendar.MINUTE,
//				// calToTime.get(Calendar.MINUTE));
//				// calToDate.set(Calendar.SECOND,
//				// calToTime.get(Calendar.SECOND));
//				// calToDate.set(Calendar.MILLISECOND,
//				// calToTime.get(Calendar.MILLISECOND));
//
//				// calFromDate.setTime(mergeDateTime(_fromDate, _fromTime));
//				// calToDate.setTime(mergeDateTime(_toDate, _toTime));
//
//				// fromDateTime = calFromDate.getTime();
//				// toDateTime = calToDate.getTime();
//
//				// logger.debug(String.format("%s=%s", fromDateTime,
//				// fromDateTime.getTime()));
//				// logger.debug(String.format("%s=%s", toDateTime,
//				// fromDateTime.getTime()));
//
//				// duration = ((toDateTime.getTime() - fromDateTime.getTime()) /
//				// 1000d / 60d / 60d);
//
//				// DateTime a = new DateTime(mergeDateTime(_fromDate,
//				// _fromTime));
//				// DateTime b = new DateTime(mergeDateTime(_toDate, _toTime));
//
//				// LocalDateTime a1 = new LocalDateTime(mergeDateTime(_fromDate,
//				// _fromTime).getTime());
//				// LocalDateTime b1 = new LocalDateTime(mergeDateTime(_toDate,
//				// _toTime).getTime());
//
//				// LocalDateTime a1 = mergeLocalDateTime(_fromDate, _fromTime);
//				// LocalDateTime b1 = mergeLocalDateTime(_toDate, _toTime);
//
//				DateTime a1 = mergeDateTime2(_fromDate, _fromTime);
//				DateTime b1 = mergeDateTime2(_toDate, _toTime);
//
//				// Duration d = new Duration(a, b);
//				Duration d1 = new Duration(a1.toDateTime(DateTimeZone.UTC), b1.toDateTime(DateTimeZone.UTC));
//
//				// logger.debug(String.format("%s", duration));
//
//				// logger.debug(d.getMillis() / 1000d / 60d / 60d);
//				logger.debug(d1.getMillis() / 1000d / 60d / 60d);
//
//				duration = d1.getMillis() / 1000d / 60d / 60d;
//
//			} else {
//
//				duration = 0d;
//
//			}
//		} catch (Exception e) {
//			logger.error("calculateDuration", e);
//			duration = 0d;
//		} finally {
//			calFromDate = null;
//			calFromTime = null;
//			calToDate = null;
//			calToTime = null;
//			fromDateTime = null;
//			toDateTime = null;
//		}
//
//		// setDuration(duration);
//		return duration;
//	}

	/**
	 * <p>mergeDateTime.</p>
	 *
	 * @param date a {@link java.util.Date} object.
	 * @param time a {@link java.util.Date} object.
	 * @return a {@link java.util.Date} object.
	 */
	public static Date mergeDateTime(Date date, Date time) {
		return new Date(date.getYear(), date.getMonth(), date.getDate(), time.getHours(), time.getMinutes(), time.getSeconds());
	}

//	public static DateTime mergeDateTime2(Date date, Date time) {
//		// setup objects
//		// LocalDate localDate = new LocalDate(date.getYear(), date.getMonth(),
//		// date.getDate());
//
//		// LocalTime localTime = new LocalTime(time.getHours(),
//		// time.getMinutes(), time.getSeconds());
//
//		// LocalDate localDate = new LocalDate(date.getTime());
//		// LocalTime localTime = new LocalTime(time.getHours(),
//		// time.getMinutes(), time.getSeconds());
//		// DateTime dt = localDate.toDateTime(localTime);
//
//		// DateTime dt = new DateTime(date.getYear(), date.getMonth(),
//		// date.getDate(), time.getHours(), time.getMinutes(),
//		// time.getSeconds());
//		// DateTime dt1 = new DateTime(date.getTime());
//		// DateTime dt2 = new DateTime(time.getTime());
//
//		// DateTime dt = new DateTime(dt1.getYear(), dt1.getMonthOfYear(),
//		// dt1.getDayOfMonth(), dt2.getHourOfDay(), dt2.getMinuteOfHour(),
//		// dt2.getSecondOfMinute());
//
//		LocalDate localDate = new LocalDate(date.getTime());
//
//		int year = localDate.getYear(); // date.getYear();
//		int month = localDate.getMonthOfYear(); // date.getMonth();
//		int day = localDate.getDayOfMonth(); // date.getDate();
//		int h = time.getHours();
//		int m = time.getMinutes();
//		int s = time.getSeconds();
//
//		logger.debug(String.format("%s-%s-%s %s:%s:%s", year, month, day, h, m, s));
//
//		DateTime dt = new DateTime(year, month, day, h, m, s, DateTimeZone.UTC);
//
//		return dt;
//	}

//	public static LocalDateTime mergeLocalDateTime(Date date, Date time) {
//		// return new LocalDateTime(mergeDateTime(date, time));
//		return new LocalDateTime(mergeDateTime2(date, time));
//	}
//
//	/**
//	 *
//	 * @param date
//	 * @param time
//	 * @return
//	 */
//	public static DateTime mergeDateTimeUTC(Date date, Date time) {
//		// return new Date(date.getYear(), date.getMonth(), date.getDate(),
//		// time.getHours(), time.getMinutes(), time.getSeconds());
//
//		LocalDateTime a1 = new LocalDateTime(mergeDateTime(date, time).getTime());
//		// LocalDateTime b1 = new LocalDateTime(mergeDateTime(_toDate,
//		// _toTime).getTime());
//
//		// Duration d = new Duration(a, b);
//		// Duration d1 = new Duration(a1.toDateTime(DateTimeZone.UTC),
//		// b1.toDateTime(DateTimeZone.UTC));
//
//		// Calendar calDate =
//		// GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
//		// Calendar calTime =
//		// GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
//		//
//		// calDate.setTime(date);
//		// calTime.setTime(time);
//		// calDate.set(Calendar.HOUR_OF_DAY, calTime.get(Calendar.HOUR_OF_DAY));
//		// calDate.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
//		// calDate.set(Calendar.SECOND, calTime.get(Calendar.SECOND));
//		// calDate.set(Calendar.MILLISECOND, calTime.get(Calendar.MILLISECOND));
//		//
//		//
//		// return calDate.getTime();
//
//		return a1.toDateTime(DateTimeZone.UTC);
//	}

	// public static Date addDays(Date aDate, int days) {
	//
	// Calendar cal = new GregorianCalendar();
	// cal.setTime(aDate);
	//
	// cal.add(Calendar.DATE, days);
	//
	// return cal.getTime();
	// }

	/**
	 * <p>minDate.</p>
	 *
	 * @return a {@link java.util.Date} object.
	 */
	public static Date minDate() {
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(new Date(0l));
		// // return calendar.get(Calendar.ERA);
		// return calendar.getTime();

		return new Date(0l);
	}

	// /**
	// * Aggiunge 12:00 per evitare problemi con CET CEST. Una data 20120309
	// * potrebbe diventare 2012 03 08 perche viene valorizzata con ora 00:00 e
	// * quando la si formatta nel locale Italiano Java la fa diventare il
	// giorno
	// * precedente passando da CET a CEST
	// *
	// * @param aIsoString
	// * @return
	// * @throws ParseException
	// */
	// public static Date parseISO(String aIsoString) throws ParseException {
	// return new SimpleDateFormat(DateUtils.FORMAT_ISO_HHmm).parse(aIsoString +
	// " 12:00");
	// }

	/*
	 *
	 *
	 *
	 *
	 * FROM it.attocchi package DateUtils
	 */

	// /**
	// * Ritorna il mese della data specificata non 0 based
	// *
	// * @param aDate
	// * @return
	// */
	// public static int getMese(Date aDate) {
	// return getMeseZeroBased(aDate) + 1;
	// }
	//
	// /**
	// * Ritorna il giorno del mese della data specificata
	// *
	// * @param aDate
	// * @return
	// */
	// public static int getGiorno(Date aDate) {
	//
	// Calendar tempCal = new GregorianCalendar();
	// tempCal.setTime(aDate);
	//
	// return tempCal.get(Calendar.DAY_OF_MONTH);
	// }
	//
	// public static String getNowFormatTZGMT() {
	// return getFormatTZGMT(new GregorianCalendar().getTime());
	// }
	//
	// public static String getFormatTZGMT(Date aDate) {
	//
	// SimpleDateFormat dateFormatGmt = new SimpleDateFormat(FORMAT_ISO_8601);
	// dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
	//
	// return dateFormatGmt.format(aDate);
	// }
	//
	// /**
	// * Ritorna l'ora della Data nel formato HH:mm
	// *
	// * @param aDate
	// * @return
	// */
	// public static String getOra(Date aDate) {
	// return new SimpleDateFormat("HH:mm").format(aDate);
	// }
	//
	// public static int differenzaInGiorni(Date start, Date end) {
	// int res = 0;
	//
	// DateTime startDate = new DateTime(start);
	// DateTime endDate = new DateTime(end);
	//
	// Days d = Days.daysBetween(startDate, endDate);
	// res = d.getDays();
	//
	// return res;
	// }
	//
	// public static List<Date> calcolaDateIntermedie(Date start, Date end) {
	// List<Date> res = new ArrayList<Date>();
	//
	// DateTime startDate = new DateTime(start);
	// DateTime endDate = new DateTime(end);
	//
	// if (differenzaInGiorni(start, end) > 0) {
	//
	// res.add(startDate.toDate());
	// startDate = startDate.plusDays(1);
	//
	// while (differenzaInGiorni(startDate.toDate(), end) != 0) {
	// res.add(startDate.toDate());
	// startDate = startDate.plusDays(1);
	// }
	//
	// res.add(startDate.toDate());
	//
	// } else {
	// res.add(start);
	// }
	//
	// return res;
	// }
	//
	// public static boolean verificaSovrapposizione(Date dateStartApp1, Date
	// dateEndApp1, Date dateStartApp2, Date dateEndApp2, boolean
	// unEstremoPuoEssereUguale) {
	// return verificaSovrapposizione(dateStartApp1.getTime(),
	// dateEndApp1.getTime(), dateStartApp2.getTime(), dateEndApp2.getTime(),
	// unEstremoPuoEssereUguale);
	// }
	//
	// public static boolean verificaSovrapposizione(long dateStartApp1, long
	// dateEndApp1, long dateStartApp2, long dateEndApp2, boolean
	// unEstremoPuoEssereUguale) {
	// boolean result = true;
	//
	// if (unEstremoPuoEssereUguale) {
	// if (dateStartApp1 < dateStartApp2 && dateEndApp1 <= dateStartApp2) {
	// result = false;
	// } else if (dateStartApp1 >= dateEndApp2 && dateEndApp1 > dateEndApp2) {
	// result = false;
	// }
	// } else {
	// if (dateStartApp1 < dateStartApp2 && dateEndApp1 < dateStartApp2) {
	// result = false;
	// } else if (dateStartApp1 > dateEndApp2 && dateEndApp1 > dateEndApp2) {
	// result = false;
	// }
	// }
	//
	// return result;
	// }
	//
	// /*
	// * Aggiunte Gianluca
	// */
	// /**
	// * Ritorna se il giorno cade in un fine settimana cioe' sabato o domenica
	// *
	// * @param dt
	// * @return
	// */
	// public static boolean isWeekend(Date dt) {
	// Calendar cal = new GregorianCalendar();
	// cal.setTime(dt);
	// int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 7=Sabato, 1=Domenica
	// return (dayOfWeek == 7 || dayOfWeek == 1) ? true : false;
	// }
	//
	// /**
	// * <p>
	// * Checks if a date is today.
	// * </p>
	// *
	// * @param date
	// * the date, not altered, not null.
	// * @return true if the date is today.
	// * @throws IllegalArgumentException
	// * if the date is <code>null</code>
	// */
	// public static boolean isToday(Date date) {
	// return isSameDay(date, Calendar.getInstance().getTime());
	// }
	//
	// /**
	// * <p>
	// * Checks if a calendar date is today.
	// * </p>
	// *
	// * @param cal
	// * the calendar, not altered, not null
	// * @return true if cal date is today
	// * @throws IllegalArgumentException
	// * if the calendar is <code>null</code>
	// */
	// public static boolean isToday(Calendar cal) {
	// return isSameDay(cal, Calendar.getInstance());
	// }
	//
	// /**
	// * <p>
	// * Checks if two calendars represent the same day ignoring time.
	// * </p>
	// *
	// * @param cal1
	// * the first calendar, not altered, not null
	// * @param cal2
	// * the second calendar, not altered, not null
	// * @return true if they represent the same day
	// * @throws IllegalArgumentException
	// * if either calendar is <code>null</code>
	// */
	// public static boolean isSameDay(Calendar cal1, Calendar cal2) {
	// if (cal1 == null || cal2 == null) {
	// throw new IllegalArgumentException("The dates must not be null");
	// }
	// return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
	// cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
	// cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
	// }
	//
	// /**
	// * <p>
	// * Checks if two dates are on the same day ignoring time.
	// * </p>
	// *
	// * @param date1
	// * the first date, not altered, not null
	// * @param date2
	// * the second date, not altered, not null
	// * @return true if they represent the same day
	// * @throws IllegalArgumentException
	// * if either date is <code>null</code>
	// */
	// public static boolean isSameDay(Date date1, Date date2) {
	// if (date1 == null || date2 == null) {
	// throw new IllegalArgumentException("The dates must not be null");
	// }
	// Calendar cal1 = Calendar.getInstance();
	// cal1.setTime(date1);
	// Calendar cal2 = Calendar.getInstance();
	// cal2.setTime(date2);
	// return isSameDay(cal1, cal2);
	// }
	//
	// public static boolean isSameMonth(Date date1, Date date2) {
	// if (date1 == null || date2 == null) {
	// throw new IllegalArgumentException("The dates must not be null");
	// }
	// Calendar cal1 = Calendar.getInstance();
	// cal1.setTime(date1);
	// Calendar cal2 = Calendar.getInstance();
	// cal2.setTime(date2);
	// return isSameDay(cal1, cal2);
	// }
	//
	// public static boolean isSameMonth(Calendar cal1, Calendar cal2) {
	// if (cal1 == null || cal2 == null) {
	// throw new IllegalArgumentException("The dates must not be null");
	// }
	// return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
	// cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
	// cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH));
	// }
	//
	// /**
	// * Settimana dell'anno
	// *
	// * @param dt
	// * @return
	// */
	// public static int WeekOfTheYear(Date dt) {
	// Calendar cal = new GregorianCalendar();
	// cal.setTime(dt);
	// return cal.get(Calendar.WEEK_OF_YEAR);
	// }
	//
	// /**
	// * Mette a zero la componente tempo della data es. 23/04/2010 24:25:01
	// * diventa 23/04/2010 00:00:00
	// *
	// * @param dt
	// * @return
	// */
	// public static Date getDateNoTime(Date dt) {
	// Calendar cal = new GregorianCalendar();
	// cal.setTime(dt);
	//
	// Calendar newcal = new GregorianCalendar();
	// newcal.set(cal.get(cal.YEAR), cal.get(cal.MONTH),
	// cal.get(cal.DAY_OF_MONTH), 0, 0, 0);
	// return newcal.getTime();
	// }
	//
	// /**
	// * ritorna il giorno della settimana
	// *
	// * @param dt
	// * @return 1 e' domenica, .. 7 sabato
	// */
	// public static int DayOfTheWeek(Date dt) {
	// Calendar cal = new GregorianCalendar();
	// cal.setTime(dt);
	// return cal.get(Calendar.DAY_OF_WEEK); // 7=Sabato, 1=Domenica
	// // int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 7=Sabato,
	// // 1=Domenica
	// // //conversione
	// // switch (dayOfWeek) {
	// // case 1: return 7;
	// // case 2: return 1;
	// // case 3: return 2;
	// // case 4: return 3;
	// // case 5: return 4;
	// // case 6: return 5;
	// // case 7: return 6;
	// // default: return 0;
	// // }
	// }
	//
	// /**
	// * Ritona il prefisso del giorno della settimana indicato
	// *
	// * @param dt
	// * @return
	// */
	// public static String DayPrefix(Date dt) {
	// Calendar cal = new GregorianCalendar();
	// cal.setTime(dt);
	// int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 7=Sabato, 1=Domenica
	//
	// switch (dayOfWeek) {
	// case 1:
	// return "Dom";
	// case 2:
	// return "Lun";
	// case 3:
	// return "Mar";
	// case 4:
	// return "Mer";
	// case 5:
	// return "Gio";
	// case 6:
	// return "Ven";
	// case 7:
	// return "Sab";
	// default:
	// return "";
	// }
	// }
	//
	// /**
	// * Ritorna la data formattata come richiesto
	// *
	// * @param dt
	// * Data da formattare
	// * @param formatString
	// * es. "dd-MM-yyyy" oppure "dd-MM-yyyy HH:mm"
	// * @return
	// */
	// public static String getFormat(Date dt, String formatString) {
	// if (dt == null)
	// return "";
	// DateFormat formatter = new SimpleDateFormat(formatString);
	// return formatter.format(dt);
	// }
	//
	// /**
	// * Ritorna la differenza in giorni tra 2 date
	// *
	// * @param startDate
	// * @param endDate
	// * @return
	// */
	// public static int DiffDays(Date startDate, Date endDate) {
	// return (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60
	// * 24));
	// }
	//
	/**
	 * <p>getDaysBetween.</p>
	 *
	 * @param d1 a {@link java.util.Date} object.
	 * @param d2 a {@link java.util.Date} object.
	 * @return a int.
	 */
	public static int getDaysBetween(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);
		return getDaysBetween(c1, c2);
	}

	//
	// /**
	// * Calculates the number of days between two calendar days in a manner
	// which
	// * is independent of the Calendar type used.
	// *
	// * @param d1
	// * The first date.
	// * @param d2
	// * The second date.
	// *
	// * @return The number of days between the two dates. Zero is returned if
	// the
	// * dates are the same, one if the dates are adjacent, etc. The order
	// * of the dates does not matter, the value returned is always >= 0.
	// * If Calendar types of d1 and d2 are different, the result may not
	// * be accurate.
	// */
	// public static int getDaysBetween(java.util.Calendar d1,
	// java.util.Calendar d2) {
	// if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
	// java.util.Calendar swap = d1;
	// d1 = d2;
	// d2 = swap;
	// }
	// int days = d2.get(java.util.Calendar.DAY_OF_YEAR) -
	// d1.get(java.util.Calendar.DAY_OF_YEAR);
	// int y2 = d2.get(java.util.Calendar.YEAR);
	// if (d1.get(java.util.Calendar.YEAR) != y2) {
	// d1 = (java.util.Calendar) d1.clone();
	// do {
	// days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
	// d1.add(java.util.Calendar.YEAR, 1);
	// } while (d1.get(java.util.Calendar.YEAR) != y2);
	// }
	// return days;
	// } // getDaysBetween()
	//
	// // public static int getPreviousQuarter(Date date) {
	// // int res = 0;
	// //
	// // int current = getQuarter(date);
	// //
	// // if (current > 1) {
	// // res = current - 1;
	// // } else if (current == 1) {
	// // res = 4;
	// // }
	// // return res;
	// // }
	//
	// public static int getQuarter(Date date) {
	// // Calendar calendar = Calendar.getInstance();
	// // calendar.setTime(date);
	// // int year = calendar.get(Calendar.YEAR);
	// // int[] months = { 3, 6, 9, 12 };
	// // int count = 0;
	// // do {
	// // calendar.set(year, months[count++], 1);
	// // Date tempDt = calendar.getTime();
	// // if (date.compareTo(tempDt) < 0) {
	// // break;
	// // }
	// // } while (count < 4);
	// // return count;
	//
	// Calendar cal = Calendar.getInstance();
	// cal.setTime(date);
	// int month = cal.get(Calendar.MONTH); /* 0 through 11 */
	// int quarter = (month / 3) + 1;
	//
	// return quarter;
	// }
	//
	// public static Date getQuarterStartDate(Date aDate) {
	// Date res = null;
	//
	// int quarter = getQuarter(aDate);
	//
	// if (quarter >= 1 && quarter <= 4) {
	// Calendar cal = new GregorianCalendar();
	// cal.set(getAnno(aDate), 3 * quarter - 3, 1);
	//
	// res = cal.getTime();
	// }
	//
	// return res;
	// }
	//
	// public static Date getPrevQuarterStartDate(Date aDate) {
	// Date res = null;
	//
	// int quarter = getQuarter(aDate);
	//
	// if (quarter >= 1 && quarter <= 4) {
	//
	// int prevQ = quarter - 1;
	// int anno = getAnno(aDate);
	//
	// if (prevQ == 0) {
	// prevQ = 4;
	// anno = anno - 1;
	// }
	//
	// Calendar cal = new GregorianCalendar();
	// cal.set(anno, 3 * prevQ - 3, 1);
	//
	// res = cal.getTime();
	// }
	//
	// return res;
	// }
	//
	// /**
	// *
	// * @param aDate
	// * @return
	// */
	// public static Date getSixMonthBefore(Date aDate) {
	// Date res = null;
	//
	// // int quarter = getQuarter(aDate);
	// //
	// // if (quarter >= 1 && quarter <= 4) {
	// // Calendar cal = new GregorianCalendar();
	// // cal.set(getAnno(aDate), 3 * quarter - 3, 1);
	// //
	// // res = cal.getTime();
	// // }
	//
	// Calendar move = GregorianCalendar.getInstance();
	// move.setTime(aDate);
	//
	// Calendar startDate = GregorianCalendar.getInstance();
	// startDate.setTime(aDate);
	//
	// int differenzaMesi = getMonthsDifference(move, startDate);
	// while (differenzaMesi < 5) {
	// move.add(Calendar.DATE, -15);
	//
	// differenzaMesi = getMonthsDifference(move, startDate);
	// }
	//
	// return getFirstDateOfMonth(move.getTime());
	// }
	//
	// public static int getMonthsDifference(Calendar date1, Calendar date2) {
	// int m1 = date1.get(Calendar.YEAR) * 12 + date1.get(Calendar.MONTH);
	// int m2 = date2.get(Calendar.YEAR) * 12 + date2.get(Calendar.MONTH);
	// return m2 - m1;
	// }
	//
	// public static Date getPrevQuarterEndDate(Date aDate) throws Exception {
	// Date res = null;
	//
	// int quarter = getQuarter(aDate);
	//
	// if (quarter >= 1 && quarter <= 4) {
	//
	// int prevQ = quarter - 1;
	// int anno = getAnno(aDate);
	//
	// if (prevQ == 0) {
	// prevQ = 4;
	// anno = anno - 1;
	// }
	//
	// Calendar cal = new GregorianCalendar();
	// cal.set(anno, 3 * prevQ - 1, getLastDayOfMont(3 * prevQ, anno));
	//
	// res = cal.getTime();
	// }
	//
	// return res;
	// }
	//
	// public static Date getFirstDateOfMonth(Date aDate) {
	// Calendar cal = new GregorianCalendar(getAnno(aDate),
	// getMeseZeroBased(aDate), 1);
	// return cal.getTime();
	// }
	//
	// public static Date getQuarterEndDate(Date aDate) throws Exception {
	// Date res = null;
	//
	// int quarter = getQuarter(aDate);
	//
	// if (quarter >= 1 && quarter <= 4) {
	// Calendar cal = new GregorianCalendar();
	// cal.set(getAnno(aDate), 3 * quarter - 1, getLastDayOfMont(3 * quarter,
	// getAnno(aDate)));
	//
	// res = cal.getTime();
	// }
	//
	// return res;
	// }
	//
	// /**
	// * Crea una data usando anno mese giorno e i mesi non zero-based
	// *
	// * @param anno
	// * @param mese
	// * @param giorno
	// * @return
	// */
	// public static Date newDate(int anno, int mese, int giorno) {
	//
	// Calendar cal = new GregorianCalendar();
	// cal.set(anno, mese - 1, giorno);
	//
	// return cal.getTime();
	// }
	//
	// /**
	// * Procedura copiata dal celendario per AppuntamentiDAO
	// *
	// * @param inizio
	// * @return
	// */
	// public static Date getInizioSettimana(Date inizio) {
	//
	// Calendar calWeekInizio = new GregorianCalendar();
	// // calWeekFine = new GregorianCalendar();
	// calWeekInizio.setTime(inizio);
	// int numWeek = 0;
	//
	// if (calWeekInizio.get(Calendar.DAY_OF_WEEK) != 1) {
	// numWeek = calWeekInizio.get(Calendar.DAY_OF_WEEK) - 2;
	// } else {
	// numWeek = 6;
	// }
	//
	// calWeekInizio.set(Calendar.DAY_OF_MONTH,
	// calWeekInizio.get(Calendar.DAY_OF_MONTH) - numWeek);
	// // calWeekFine.setTime(calWeekInizio.getTime());
	// // calWeekFine.set(Calendar.DAY_OF_MONTH,
	// // calWeekInizio.get(Calendar.DAY_OF_MONTH) + 6);
	//
	// return calWeekInizio.getTime();
	// }
	//
	// public static Date getFineSettimana(Date inizio) {
	// Calendar calWeekFine = new GregorianCalendar();
	//
	// Calendar calWeekInizo = new GregorianCalendar();
	// calWeekInizo.setTime(getInizioSettimana(inizio));
	//
	// calWeekFine.set(Calendar.DAY_OF_MONTH,
	// calWeekInizo.get(Calendar.DAY_OF_MONTH) + 6);
	//
	// return calWeekFine.getTime();
	// }
	//
	// /**
	// *
	// * @param _fromDate
	// * @param _fromTime
	// * @param _toDate
	// * @param _toTime
	// * @return
	// */
	// public static Double calculateDuration(Date _fromDate, Date _fromTime,
	// Date _toDate, Date _toTime) {
	// Double duration;
	// Calendar calFromDate = null;
	// Calendar calFromTime = null;
	// Calendar calToDate = null;
	// Calendar calToTime = null;
	// Date fromDateTime = null;
	// Date toDateTime = null;
	//
	// try {
	// if ((_fromDate != null) && (_fromTime != null) && (_toDate != null) &&
	// (_toTime != null)) {
	//
	// logger.debug(String.format("%s=%s", _fromDate, _fromDate.getTime()));
	// logger.debug(String.format("%s=%s", _fromTime, _fromTime.getTime()));
	// logger.debug(String.format("%s=%s", _toDate, _toDate.getTime()));
	// logger.debug(String.format("%s=%s", _toTime, _toTime.getTime()));
	//
	// // calFromDate = new GregorianCalendar();
	// // calFromDate.setTime(_fromDate);
	//
	// // calFromTime = new GregorianCalendar();
	// // calFromTime.setTime(_fromTime);
	//
	// // calToDate = new GregorianCalendar();
	// // calToDate.setTime(_toDate);
	//
	// // calToTime = new GregorianCalendar();
	// // calToTime.setTime(_toTime);
	//
	// // calFromDate.set(Calendar.HOUR_OF_DAY,
	// // calFromTime.get(Calendar.HOUR_OF_DAY));
	// // calFromDate.set(Calendar.MINUTE,
	// // calFromTime.get(Calendar.MINUTE));
	// // calFromDate.set(Calendar.SECOND,
	// // calFromTime.get(Calendar.SECOND));
	// // calFromDate.set(Calendar.MILLISECOND,
	// // calFromTime.get(Calendar.MILLISECOND));
	// //
	// // calToDate.set(Calendar.HOUR_OF_DAY,
	// // calToTime.get(Calendar.HOUR_OF_DAY));
	// // calToDate.set(Calendar.MINUTE,
	// // calToTime.get(Calendar.MINUTE));
	// // calToDate.set(Calendar.SECOND,
	// // calToTime.get(Calendar.SECOND));
	// // calToDate.set(Calendar.MILLISECOND,
	// // calToTime.get(Calendar.MILLISECOND));
	//
	// // calFromDate.setTime(mergeDateTime(_fromDate, _fromTime));
	// // calToDate.setTime(mergeDateTime(_toDate, _toTime));
	//
	// // fromDateTime = calFromDate.getTime();
	// // toDateTime = calToDate.getTime();
	//
	// // logger.debug(String.format("%s=%s", fromDateTime,
	// // fromDateTime.getTime()));
	// // logger.debug(String.format("%s=%s", toDateTime,
	// // fromDateTime.getTime()));
	//
	// // duration = ((toDateTime.getTime() - fromDateTime.getTime()) /
	// // 1000d / 60d / 60d);
	//
	// // DateTime a = new DateTime(mergeDateTime(_fromDate,
	// // _fromTime));
	// // DateTime b = new DateTime(mergeDateTime(_toDate, _toTime));
	//
	// // LocalDateTime a1 = new LocalDateTime(mergeDateTime(_fromDate,
	// // _fromTime).getTime());
	// // LocalDateTime b1 = new LocalDateTime(mergeDateTime(_toDate,
	// // _toTime).getTime());
	//
	// // LocalDateTime a1 = mergeLocalDateTime(_fromDate, _fromTime);
	// // LocalDateTime b1 = mergeLocalDateTime(_toDate, _toTime);
	//
	// DateTime a1 = mergeDateTime2(_fromDate, _fromTime);
	// DateTime b1 = mergeDateTime2(_toDate, _toTime);
	//
	// // Duration d = new Duration(a, b);
	// Duration d1 = new Duration(a1.toDateTime(DateTimeZone.UTC),
	// b1.toDateTime(DateTimeZone.UTC));
	//
	// // logger.debug(String.format("%s", duration));
	//
	// // logger.debug(d.getMillis() / 1000d / 60d / 60d);
	// logger.debug(d1.getMillis() / 1000d / 60d / 60d);
	//
	// duration = d1.getMillis() / 1000d / 60d / 60d;
	//
	// } else {
	//
	// duration = 0d;
	//
	// }
	// } catch (Exception e) {
	// logger.error("calculateDuration", e);
	// duration = 0d;
	// } finally {
	// calFromDate = null;
	// calFromTime = null;
	// calToDate = null;
	// calToTime = null;
	// fromDateTime = null;
	// toDateTime = null;
	// }
	//
	// // setDuration(duration);
	// return duration;
	// }
	//
	// public static Date mergeDateTime(Date date, Date time) {
	// return new Date(date.getYear(), date.getMonth(), date.getDate(),
	// time.getHours(), time.getMinutes(), time.getSeconds());
	// }
	//
	// public static DateTime mergeDateTime2(Date date, Date time) {
	// // setup objects
	// // LocalDate localDate = new LocalDate(date.getYear(), date.getMonth(),
	// // date.getDate());
	//
	// // LocalTime localTime = new LocalTime(time.getHours(),
	// // time.getMinutes(), time.getSeconds());
	//
	// // LocalDate localDate = new LocalDate(date.getTime());
	// // LocalTime localTime = new LocalTime(time.getHours(),
	// // time.getMinutes(), time.getSeconds());
	// // DateTime dt = localDate.toDateTime(localTime);
	//
	// // DateTime dt = new DateTime(date.getYear(), date.getMonth(),
	// // date.getDate(), time.getHours(), time.getMinutes(),
	// // time.getSeconds());
	// // DateTime dt1 = new DateTime(date.getTime());
	// // DateTime dt2 = new DateTime(time.getTime());
	//
	// // DateTime dt = new DateTime(dt1.getYear(), dt1.getMonthOfYear(),
	// // dt1.getDayOfMonth(), dt2.getHourOfDay(), dt2.getMinuteOfHour(),
	// // dt2.getSecondOfMinute());
	//
	// LocalDate localDate = new LocalDate(date.getTime());
	//
	// int year = localDate.getYear(); // date.getYear();
	// int month = localDate.getMonthOfYear(); // date.getMonth();
	// int day = localDate.getDayOfMonth(); // date.getDate();
	// int h = time.getHours();
	// int m = time.getMinutes();
	// int s = time.getSeconds();
	//
	// logger.info(String.format("%s-%s-%s %s:%s:%s", year, month, day, h, m,
	// s));
	//
	// DateTime dt = new DateTime(year, month, day, h, m, s, DateTimeZone.UTC);
	//
	// return dt;
	// }
	//
	// public static LocalDateTime mergeLocalDateTime(Date date, Date time) {
	// // return new LocalDateTime(mergeDateTime(date, time));
	// return new LocalDateTime(mergeDateTime2(date, time));
	// }
	//
	// /**
	// *
	// * @param date
	// * @param time
	// * @return
	// */
	// public static DateTime mergeDateTimeUTC(Date date, Date time) {
	// // return new Date(date.getYear(), date.getMonth(), date.getDate(),
	// // time.getHours(), time.getMinutes(), time.getSeconds());
	//
	// LocalDateTime a1 = new LocalDateTime(mergeDateTime(date,
	// time).getTime());
	// // LocalDateTime b1 = new LocalDateTime(mergeDateTime(_toDate,
	// // _toTime).getTime());
	//
	// // Duration d = new Duration(a, b);
	// // Duration d1 = new Duration(a1.toDateTime(DateTimeZone.UTC),
	// // b1.toDateTime(DateTimeZone.UTC));
	//
	// // Calendar calDate =
	// // GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
	// // Calendar calTime =
	// // GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
	// //
	// // calDate.setTime(date);
	// // calTime.setTime(time);
	// // calDate.set(Calendar.HOUR_OF_DAY, calTime.get(Calendar.HOUR_OF_DAY));
	// // calDate.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
	// // calDate.set(Calendar.SECOND, calTime.get(Calendar.SECOND));
	// // calDate.set(Calendar.MILLISECOND, calTime.get(Calendar.MILLISECOND));
	// //
	// //
	// // return calDate.getTime();
	//
	// return a1.toDateTime(DateTimeZone.UTC);
	// }
	//
	// public static Date minDate() {
	// // Calendar calendar = Calendar.getInstance();
	// // calendar.setTime(new Date(0l));
	// // // return calendar.get(Calendar.ERA);
	// // return calendar.getTime();
	//
	// return new Date(0l);
	// }

//	/**
//	 * http://joda-time.sourceforge.net/apidocs/org/joda/time/format/
//	 * DateTimeFormat.html
//	 *
//	 * @param aDate
//	 * @return
//	 */
//	public static String getWeekOfTheYearKey(Date aDate) {
//		DateTime dateTime = new DateTime(aDate);
//		DateTimeFormatter fmt = DateTimeFormat.forPattern("x-'W'w");
//		return fmt.print(dateTime);
//
//	}
//
//	/**
//	 * http://stackoverflow.com/questions/15358409/dividing-a-joda-time-period-
//	 * into-intervals-of-desired-size
//	 *
//	 * @param start
//	 * @param end
//	 * @param chunkAmount
//	 * @param chunkSize
//	 * @return
//	 */
//	public static List<Interval> splitDuration(DateTime start, DateTime end, long chunkAmount, long chunkSize) {
//		long millis = start.getMillis();
//		List<Interval> list = new ArrayList<Interval>();
//
//		// for(int i = 0; i < chunkAmount; ++i) {
//		// list.add(new Interval(millis, millis += chunkSize));
//		// }
//
//		while (millis <= end.getMillis()) {
//			list.add(new Interval(millis, millis += chunkSize));
//		}
//
//		if (millis < end.getMillis())
//			list.add(new Interval(millis, end.getMillis()));
//
//		return list;
//	}
//
//	/**
//	 * verifica se una data e' diversa da vuota e superiore ad adesso
//	 *
//	 * @param aDate
//	 * @return
//	 */
//	public static boolean greaterThanToday(Date aDate) {
//		return aDate != null && new DateTime(aDate).toDateMidnight().isAfterNow();
//	}

	/**
	 * <p>getSemester.</p>
	 *
	 * @param date a {@link java.util.Date} object.
	 * @return a int.
	 */
	public static int getSemester(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH); /* 0 through 11 */
		int quarter = (month / 6) + 1;
		return quarter;
	}

	/**
	 * <p>getSemesterStartDate.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a {@link java.util.Date} object.
	 */
	public static Date getSemesterStartDate(Date aDate) {
		Date res = null;
		int semester = getSemester(aDate);
		if (semester >= 1 && semester <= 7) {
			Calendar cal = new GregorianCalendar();
			cal.set(getAnno(aDate), 6 * semester - 6, 1);
			res = cal.getTime();
		}
		return res;
	}

	/**
	 * <p>getPrevSemesterStartDate.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a {@link java.util.Date} object.
	 */
	public static Date getPrevSemesterStartDate(Date aDate) {
		Date res = null;
		int semester = getSemester(aDate);
		if (semester >= 1 && semester <= 2) {
			int prevS = semester - 1;
			int anno = getAnno(aDate);
			if (prevS == 0) {
				prevS = 2;
				anno = anno - 1;
			}
			Calendar cal = new GregorianCalendar();
			cal.set(anno, 6 * prevS - 6, 1);
			res = cal.getTime();
		}
		return res;
	}

	/**
	 * <p>getSemesterEndDate.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a {@link java.util.Date} object.
	 * @throws java.lang.Exception if any.
	 */
	public static Date getSemesterEndDate(Date aDate) throws Exception {
		Date res = null;
		int semester = getSemester(aDate);
		if (semester >= 1 && semester <= 2) {
			Calendar cal = new GregorianCalendar();
			cal.set(getAnno(aDate), 6 * semester - 1, getLastDayOfMont(6 * semester, getAnno(aDate)));
			res = cal.getTime();
		}
		return res;
	}

	/**
	 * <p>getPrevSemesterEndDate.</p>
	 *
	 * @param aDate a {@link java.util.Date} object.
	 * @return a {@link java.util.Date} object.
	 * @throws java.lang.Exception if any.
	 */
	public static Date getPrevSemesterEndDate(Date aDate) throws Exception {
		Date res = null;
		int semester = getSemester(aDate);
		if (semester >= 1 && semester <= 7) {
			int prevS = semester - 1;
			int anno = getAnno(aDate);
			if (prevS == 0) {
				prevS = 2;
				anno = anno - 1;
			}
			Calendar cal = new GregorianCalendar();
			cal.set(anno, 6 * prevS - 1, getLastDayOfMont(6 * prevS, anno));
			res = cal.getTime();
		}
		return res;
	}

	/**
	 * <p>getTimestampMS.</p>
	 *
	 * @return the number of milliseconds since January 1, 1970, 00:00:00 GMT represented by this date.
	 */
	public static synchronized long getTimestampMS() {
		return new Date().getTime();
	}
}
