package it.attocchi.utils;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class Crono {

	private static Map<String, Date> starts = new HashMap<String, Date>();
	private static Map<String, Date> stops = new HashMap<String, Date>();

	public static void start(String name) {
		// if (starts == null)
		// starts = new HashMap<String, Date>();

		starts.put(name, new Date());
	}

	private static void stop(String name) {
		// if (stops == null)
		// stops = new HashMap<String, Date>();

		stops.put(name, new Date());

	}

	public static String stopAndLog(String name) {
		String res = "Crono \"" + name + "\" not available.";

		stop(name);

		if (starts.containsKey(name)) {
			Date start = starts.get(name);
			Date stop = stops.get(name);

			long diff = stop.getTime() - start.getTime();

			if (diff < 1000) {
				res = "Crono " + name + ": " + diff + "ms";
			} else {
				double seconds = diff / 1000d;
				res = "Crono " + name + ": " + new DecimalFormat("#.##").format(seconds) + "s";
			}

			// res = "Crono " + name + ": " + diff + "ms";
		}

		return res;
	}

	public static void stopAndLogDebug(String name, Logger logger) {
		logger.debug(stopAndLog(name));
	}

	public static void stopAndSysOut(String name) {
		System.out.println(stopAndLog(name));
	}
}
