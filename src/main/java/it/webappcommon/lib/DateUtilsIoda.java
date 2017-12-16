package it.webappcommon.lib;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * DateUtils that extends {@link DateUtilsLT} with use of {@link org.joda.time}
 * 
 * @author mirco
 *
 */
public class DateUtilsIoda extends DateUtils {

	public static int differenzaInGiorni(Date start, Date end) {
		int res = 0;

		DateTime startDate = new DateTime(start);
		DateTime endDate = new DateTime(end);

		Days giorni = Days.daysBetween(startDate, endDate);
		res = giorni.getDays();

		return res;
	}

	public static int differenzaInSettimane(Date start, Date end) {
		int res = 0;

		DateTime startDate = new DateTime(start);
		DateTime endDate = new DateTime(end);

		Weeks settimane = Weeks.weeksBetween(startDate, endDate);
		res = settimane.getWeeks();

		return res;
	}

	public static int differenzaInMesi(Date start, Date end) {
		int res = 0;

		DateTime startDate = new DateTime(start);
		DateTime endDate = new DateTime(end);

		Months mesi = Months.monthsBetween(startDate, endDate);
		res = mesi.getMonths();

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

		logger.debug(String.format("%s-%s-%s %s:%s:%s", year, month, day, h, m, s));

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
	
	/**
	 * http://joda-time.sourceforge.net/apidocs/org/joda/time/format/
	 * DateTimeFormat.html
	 * 
	 * @param aDate
	 * @return
	 */
	public static String getWeekOfTheYearKey(Date aDate) {
		DateTime dateTime = new DateTime(aDate);
		DateTimeFormatter fmt = DateTimeFormat.forPattern("x-'W'w");
		return fmt.print(dateTime);

	}

	/**
	 * http://stackoverflow.com/questions/15358409/dividing-a-joda-time-period-
	 * into-intervals-of-desired-size
	 * 
	 * @param start
	 * @param end
	 * @param chunkAmount
	 * @param chunkSize
	 * @return
	 */
	public static List<Interval> splitDuration(DateTime start, DateTime end, long chunkAmount, long chunkSize) {
		long millis = start.getMillis();
		List<Interval> list = new ArrayList<Interval>();

		// for(int i = 0; i < chunkAmount; ++i) {
		// list.add(new Interval(millis, millis += chunkSize));
		// }

		while (millis <= end.getMillis()) {
			list.add(new Interval(millis, millis += chunkSize));
		}

		if (millis < end.getMillis())
			list.add(new Interval(millis, end.getMillis()));

		return list;
	}

	/**
	 * verifica se una data e' diversa da vuota e superiore ad adesso
	 * 
	 * @param aDate
	 * @return
	 */
	public static boolean greaterThanToday(Date aDate) {
		return aDate != null && new DateTime(aDate).toDateMidnight().isAfterNow();
	}	
}
