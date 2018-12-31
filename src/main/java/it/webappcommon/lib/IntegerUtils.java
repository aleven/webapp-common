package it.webappcommon.lib;

/**
 * <p>IntegerUtils class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class IntegerUtils {

	/**
	 * <p>max.</p>
	 *
	 * @param ints a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
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

	/**
	 * <p>sum.</p>
	 *
	 * @param ints a {@link java.lang.Integer} object.
	 * @return a {@link java.lang.Integer} object.
	 */
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

	/**
	 * <p>tryParse.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 * @param defaultValue a int.
	 * @return a int.
	 */
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
