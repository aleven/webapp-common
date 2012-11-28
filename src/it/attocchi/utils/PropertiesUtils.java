package it.attocchi.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesUtils {

	protected static final Logger logger = Logger.getLogger(PropertiesUtils.class.getName());

	/**
	 * 
	 * @param in
	 *            InputStrem like a getResourceAsStrem from ServletContext
	 * @return "new Properties();" also in case of error
	 */
	public static Properties getProperties(InputStream in) {

		Properties properties = new Properties();

		try {

			// InputStream in =
			// getServletContext().getResourceAsStream(WEB_INF_CONFIG_PORTAL_PROPERTIES);
			properties.load(in);
			// in.close();

		} catch (Exception ex) {
			logger.error("Error loading properties", ex);
			properties = null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception ex) {
					logger.error("Error closing InputStream", ex);
				}
			}
		}

		return properties;
	}

	public static void saveProperties(Properties properties, String filePath) {

		// Properties properties = new Properties();

		FileOutputStream out = null;
		try {

			File f = new File(filePath);
			out = new FileOutputStream(f);
			properties.store(out, "---No Comment---");
			out.close();

		} catch (Exception ex) {
			logger.error("Error saving properties", ex);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception ex) {
					logger.error("Error closing FileOutputStream", ex);
				}
			}
		}

	}
}
