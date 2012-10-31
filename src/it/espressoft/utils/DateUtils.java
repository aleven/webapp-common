package it.espressoft.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

public class DateUtils extends DateUtilsLT {

	/**
	 * 
	 * @param aDate
	 * @return
	 */
	public static int getAnno(Date aDate) {

		Calendar tempCal = new GregorianCalendar();
		tempCal.setTime(aDate);

		return tempCal.get(Calendar.YEAR);
	}

	/**
	 * Ritorna il mese della data specificata non 0 based
	 * 
	 * @param aDate
	 * @return
	 */
	public static int getMese(Date aDate) {
		return getMeseZeroBased(aDate) + 1;
	}

	public static int getMeseZeroBased(Date aDate) {

		Calendar tempCal = new GregorianCalendar();
		tempCal.setTime(aDate);

		return tempCal.get(Calendar.MONTH);
	}

	/**
	 * Ritorna il giorno del mese della data specificata
	 * 
	 * @param aDate
	 * @return
	 */
	public static int getGiorno(Date aDate) {

		Calendar tempCal = new GregorianCalendar();
		tempCal.setTime(aDate);

		return tempCal.get(Calendar.DAY_OF_MONTH);
	}

	public static String getNowFormatTZGMT() {
		return getFormatTZGMT(new GregorianCalendar().getTime());
	}

	public static String getFormatTZGMT(Date aDate) {

		SimpleDateFormat dateFormatGmt = new SimpleDateFormat(FORMAT_ISO_8601);
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

		return dateFormatGmt.format(aDate);
	}

	/**
	 * Ritorna l'ora della Data nel formato HH:mm
	 * 
	 * @param aDate
	 * @return
	 */
	public static String getOra(Date aDate) {
		return new SimpleDateFormat("HH:mm").format(aDate);
	}

	public static int differenzaInGiorni(Date start, Date end) {
		int res = 0;

		DateTime startDate = new DateTime(start);
		DateTime endDate = new DateTime(end);

		Days d = Days.daysBetween(startDate, endDate);
		res = d.getDays();

		return res;
	}

	public static List<Date> calcolaDateIntermedie(Date start, Date end) {
		List<Date> res = new ArrayList<Date>();

		DateTime startDate = new DateTime(start);
		DateTime endDate = new DateTime(end);

		if (differenzaInGiorni(start, end) > 0) {

			res.add(startDate.toDate());
			startDate = startDate.plusDays(1);

			while (differenzaInGiorni(startDate.toDate(), end) != 0) {
				res.add(startDate.toDate());
				startDate = startDate.plusDays(1);
			}

			res.add(startDate.toDate());

		} else {
			res.add(start);
		}

		return res;
	}

	public static boolean verificaSovrapposizione(Date dateStartApp1, Date dateEndApp1, Date dateStartApp2, Date dateEndApp2, boolean unEstremoPuoEssereUguale) {
		return verificaSovrapposizione(dateStartApp1.getTime(), dateEndApp1.getTime(), dateStartApp2.getTime(), dateEndApp2.getTime(), unEstremoPuoEssereUguale);
	}

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
	 * Ritorna se il giorno cade in un fine settimana cioè sabato o domenica
	 * 
	 * @param dt
	 * @return
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
	 * @throws IllegalArgumentException
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
	 * @throws IllegalArgumentException
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
	 * @throws IllegalArgumentException
	 *             if either calendar is <code>null</code>
	 */
	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
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
	 * @throws IllegalArgumentException
	 *             if either date is <code>null</code>
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return isSameDay(cal1, cal2);
	}

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

	public static boolean isSameMonth(Calendar cal1, Calendar cal2) {
		if (cal1 == null || cal2 == null) {
			throw new IllegalArgumentException("The dates must not be null");
		}
		return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH));
	}

	/**
	 * Settimana dell'anno
	 * 
	 * @param dt
	 * @return
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
	 * @param dt
	 * @return
	 */
	public static Date getDateNoTime(Date dt) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(dt);

		Calendar newcal = new GregorianCalendar();
		newcal.set(cal.get(cal.YEAR), cal.get(cal.MONTH), cal.get(cal.DAY_OF_MONTH), 0, 0, 0);
		return newcal.getTime();
	}

	/**
	 * ritorna il giorno della settimana
	 * 
	 * @param dt
	 * @return 1 è domenica, .. 7 sabato
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
	 * @param dt
	 * @return
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
	 * @return
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
	 * @param startDate
	 * @param endDate
	 * @return
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
	 * 
	 * @return The number of days between the two dates. Zero is returned if the
	 *         dates are the same, one if the dates are adjacent, etc. The order
	 *         of the dates does not matter, the value returned is always >= 0.
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
	 * 
	 * @param aDate
	 * @return
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

	public static int getMonthsDifference(Calendar date1, Calendar date2) {
		int m1 = date1.get(Calendar.YEAR) * 12 + date1.get(Calendar.MONTH);
		int m2 = date2.get(Calendar.YEAR) * 12 + date2.get(Calendar.MONTH);
		return m2 - m1;
	}

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

	public static Date getFirstDateOfMonth(Date aDate) {
		Calendar cal = new GregorianCalendar(getAnno(aDate), getMeseZeroBased(aDate), 1);
		return cal.getTime();
	}

	public static Date getLastDateOfMonth(Date aDate) throws Exception {

		Calendar cal = new GregorianCalendar(getAnno(aDate), getMeseZeroBased(aDate), getLastDayOfMontZeroBased(getMeseZeroBased(aDate), getAnno(aDate)));
		return cal.getTime();
	}

	/**
	 * 
	 * @param monthZeroBased
	 *            numero del mese zero-based
	 * @return
	 */
	public static int getLastDayOfMontZeroBased(int monthZeroBased, int year) throws Exception {
		return getLastDayOfMont(monthZeroBased + 1, year);
	}

	/**
	 * 
	 * @param monthNonZeroBased
	 *            Numero del mese non zero-based
	 * @return
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
	 * @param anno
	 * @param mese
	 * @param giorno
	 * @return
	 */
	public static Date newDate(int anno, int mese, int giorno) {

		Calendar cal = new GregorianCalendar();
		cal.set(anno, mese - 1, giorno);

		return cal.getTime();
	}

	/**
	 * Procedura copiata dal celendario per AppuntamentiDAO
	 * 
	 * @param inizio
	 * @return
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

	public static Date getFineSettimana(Date inizio) {
		Calendar calWeekFine = new GregorianCalendar();

		Calendar calWeekInizo = new GregorianCalendar();
		calWeekInizo.setTime(getInizioSettimana(inizio));

		calWeekFine.set(Calendar.DAY_OF_MONTH, calWeekInizo.get(Calendar.DAY_OF_MONTH) + 6);

		return calWeekFine.getTime();
	}

	/**
	 * 
	 * @param _fromDate
	 * @param _fromTime
	 * @param _toDate
	 * @param _toTime
	 * @return
	 */
	public static Double calculateDuration(Date _fromDate, Date _fromTime, Date _toDate, Date _toTime) {
		Double duration;
		Calendar calFromDate = null;
		Calendar calFromTime = null;
		Calendar calToDate = null;
		Calendar calToTime = null;
		Date fromDateTime = null;
		Date toDateTime = null;

		try {
			if ((_fromDate != null) && (_fromTime != null) && (_toDate != null) && (_toTime != null)) {

				logger.debug(String.format("%s=%s", _fromDate, _fromDate.getTime()));
				logger.debug(String.format("%s=%s", _fromTime, _fromTime.getTime()));
				logger.debug(String.format("%s=%s", _toDate, _toDate.getTime()));
				logger.debug(String.format("%s=%s", _toTime, _toTime.getTime()));

				// calFromDate = new GregorianCalendar();
				// calFromDate.setTime(_fromDate);

				// calFromTime = new GregorianCalendar();
				// calFromTime.setTime(_fromTime);

				// calToDate = new GregorianCalendar();
				// calToDate.setTime(_toDate);

				// calToTime = new GregorianCalendar();
				// calToTime.setTime(_toTime);

				// calFromDate.set(Calendar.HOUR_OF_DAY,
				// calFromTime.get(Calendar.HOUR_OF_DAY));
				// calFromDate.set(Calendar.MINUTE,
				// calFromTime.get(Calendar.MINUTE));
				// calFromDate.set(Calendar.SECOND,
				// calFromTime.get(Calendar.SECOND));
				// calFromDate.set(Calendar.MILLISECOND,
				// calFromTime.get(Calendar.MILLISECOND));
				//
				// calToDate.set(Calendar.HOUR_OF_DAY,
				// calToTime.get(Calendar.HOUR_OF_DAY));
				// calToDate.set(Calendar.MINUTE,
				// calToTime.get(Calendar.MINUTE));
				// calToDate.set(Calendar.SECOND,
				// calToTime.get(Calendar.SECOND));
				// calToDate.set(Calendar.MILLISECOND,
				// calToTime.get(Calendar.MILLISECOND));

				// calFromDate.setTime(mergeDateTime(_fromDate, _fromTime));
				// calToDate.setTime(mergeDateTime(_toDate, _toTime));

				// fromDateTime = calFromDate.getTime();
				// toDateTime = calToDate.getTime();

				// logger.debug(String.format("%s=%s", fromDateTime,
				// fromDateTime.getTime()));
				// logger.debug(String.format("%s=%s", toDateTime,
				// fromDateTime.getTime()));

				// duration = ((toDateTime.getTime() - fromDateTime.getTime()) /
				// 1000d / 60d / 60d);

				// DateTime a = new DateTime(mergeDateTime(_fromDate,
				// _fromTime));
				// DateTime b = new DateTime(mergeDateTime(_toDate, _toTime));

				// LocalDateTime a1 = new LocalDateTime(mergeDateTime(_fromDate,
				// _fromTime).getTime());
				// LocalDateTime b1 = new LocalDateTime(mergeDateTime(_toDate,
				// _toTime).getTime());

				// LocalDateTime a1 = mergeLocalDateTime(_fromDate, _fromTime);
				// LocalDateTime b1 = mergeLocalDateTime(_toDate, _toTime);

				DateTime a1 = mergeDateTime2(_fromDate, _fromTime);
				DateTime b1 = mergeDateTime2(_toDate, _toTime);

				// Duration d = new Duration(a, b);
				Duration d1 = new Duration(a1.toDateTime(DateTimeZone.UTC), b1.toDateTime(DateTimeZone.UTC));

				// logger.debug(String.format("%s", duration));

				// logger.debug(d.getMillis() / 1000d / 60d / 60d);
				logger.debug(d1.getMillis() / 1000d / 60d / 60d);

				duration = d1.getMillis() / 1000d / 60d / 60d;

			} else {

				duration = 0d;

			}
		} catch (Exception e) {
			logger.error("calculateDuration", e);
			duration = 0d;
		} finally {
			calFromDate = null;
			calFromTime = null;
			calToDate = null;
			calToTime = null;
			fromDateTime = null;
			toDateTime = null;
		}

		// setDuration(duration);
		return duration;
	}

	public static Date mergeDateTime(Date date, Date time) {
		return new Date(date.getYear(), date.getMonth(), date.getDate(), time.getHours(), time.getMinutes(), time.getSeconds());
	}

	public static DateTime mergeDateTime2(Date date, Date time) {
		// setup objects
		// LocalDate localDate = new LocalDate(date.getYear(), date.getMonth(),
		// date.getDate());

		// LocalTime localTime = new LocalTime(time.getHours(),
		// time.getMinutes(), time.getSeconds());

		// LocalDate localDate = new LocalDate(date.getTime());
		// LocalTime localTime = new LocalTime(time.getHours(),
		// time.getMinutes(), time.getSeconds());
		// DateTime dt = localDate.toDateTime(localTime);

		// DateTime dt = new DateTime(date.getYear(), date.getMonth(),
		// date.getDate(), time.getHours(), time.getMinutes(),
		// time.getSeconds());
		// DateTime dt1 = new DateTime(date.getTime());
		// DateTime dt2 = new DateTime(time.getTime());

		// DateTime dt = new DateTime(dt1.getYear(), dt1.getMonthOfYear(),
		// dt1.getDayOfMonth(), dt2.getHourOfDay(), dt2.getMinuteOfHour(),
		// dt2.getSecondOfMinute());

		LocalDate localDate = new LocalDate(date.getTime());

		int year = localDate.getYear(); // date.getYear();
		int month = localDate.getMonthOfYear(); // date.getMonth();
		int day = localDate.getDayOfMonth(); // date.getDate();
		int h = time.getHours();
		int m = time.getMinutes();
		int s = time.getSeconds();

		logger.info(String.format("%s-%s-%s %s:%s:%s", year, month, day, h, m, s));

		DateTime dt = new DateTime(year, month, day, h, m, s, DateTimeZone.UTC);

		return dt;
	}

	public static LocalDateTime mergeLocalDateTime(Date date, Date time) {
		// return new LocalDateTime(mergeDateTime(date, time));
		return new LocalDateTime(mergeDateTime2(date, time));
	}

	/**
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
	public static DateTime mergeDateTimeUTC(Date date, Date time) {
		// return new Date(date.getYear(), date.getMonth(), date.getDate(),
		// time.getHours(), time.getMinutes(), time.getSeconds());

		LocalDateTime a1 = new LocalDateTime(mergeDateTime(date, time).getTime());
		// LocalDateTime b1 = new LocalDateTime(mergeDateTime(_toDate,
		// _toTime).getTime());

		// Duration d = new Duration(a, b);
		// Duration d1 = new Duration(a1.toDateTime(DateTimeZone.UTC),
		// b1.toDateTime(DateTimeZone.UTC));

		// Calendar calDate =
		// GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
		// Calendar calTime =
		// GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT"));
		//
		// calDate.setTime(date);
		// calTime.setTime(time);
		// calDate.set(Calendar.HOUR_OF_DAY, calTime.get(Calendar.HOUR_OF_DAY));
		// calDate.set(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
		// calDate.set(Calendar.SECOND, calTime.get(Calendar.SECOND));
		// calDate.set(Calendar.MILLISECOND, calTime.get(Calendar.MILLISECOND));
		//
		//
		// return calDate.getTime();

		return a1.toDateTime(DateTimeZone.UTC);
	}

	public static Date addDays(Date aDate, int days) {

		Calendar cal = new GregorianCalendar();
		cal.setTime(aDate);

		cal.add(Calendar.DATE, days);

		return cal.getTime();
	}

	public static Date minDate() {
		// Calendar calendar = Calendar.getInstance();
		// calendar.setTime(new Date(0l));
		// // return calendar.get(Calendar.ERA);
		// return calendar.getTime();

		return new Date(0l);
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
}
