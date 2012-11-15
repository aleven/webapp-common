package it.espressoft.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class DateUtilsLT {
	
	protected static final Logger logger = Logger.getLogger(DateUtils.class.getName());

	public static final String FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	public final static String FORMAT_ISO = "yyyyMMdd";

	public final static String FORMAT_ISO_HHMM = "yyyyMMdd HH:mm";

	public static final String FORMAT_HHmmssSS = "HH:mm:ss:SS";

	public static Date Now() {
		return new Date();
	}
	
	public static String Now(String pattern) {
		return new SimpleDateFormat(pattern).format(Now());
	}

	public static String getNowFormatISO() {
		return new SimpleDateFormat(FORMAT_ISO).format(Now());
	}

}
