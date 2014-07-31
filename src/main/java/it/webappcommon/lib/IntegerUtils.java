package it.webappcommon.lib;

public class IntegerUtils {

	public static Integer max(Integer... ints) {
		Integer res = null;

		for (Integer i : ints) {
			if (null != i) {
				if (res == null || i > res) {
					res = i;
				}
			}
		}

		return res;
	}

	public static Integer sum(Integer... ints) {
		Integer res = null;

		for (Integer i : ints) {
			if (null != i) {
				if (res == null) {
					res = i;
				} else {
					res = res + i;
				}
			}
		}

		return res;
	}

	public static int tryParse(String value, int defaultValue) {
		int res = defaultValue;
		try {
			if (value != null && !value.trim().isEmpty())
				res = Integer.parseInt(value);
		} catch (Exception ex) {
			//
		}
		return res;
	}

}
