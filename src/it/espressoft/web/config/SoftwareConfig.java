package it.espressoft.web.config;

import it.espressoft.jsf2.PageBase;
import it.espressoft.utils.PropertiesUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

public class SoftwareConfig {

	protected static final Logger logger = Logger.getLogger(SoftwareConfig.class.getName());

	public static final String WEB_INF_CONFIG_PROPERTIES = "/WEB-INF/software.properties";
	// public static final String PU_NAME = "AtreeFLOW-WS";
	public static final String PU_NAME = "DEFAULT_PU";

	private static Properties properties;

	public static Properties getProperties() {
		return properties;
	}

	public static void setProperties(Properties properties) {
		SoftwareConfig.properties = properties;
	}

	private static Map<String, String> jpaDbProps;

	public static Map<String, String> getJpaDbProps() {
		return jpaDbProps;
	}

	public static void setJpaDbProps(Map<String, String> dbProps) {
		SoftwareConfig.jpaDbProps = dbProps;
	}

	/**
	 * Inizializza i Valori della BL e della PersistenceUnit
	 */
	public static void init(ServletContext servletContext) {

		if (jpaDbProps == null) {

			logger.warn("Loading " + WEB_INF_CONFIG_PROPERTIES);
			properties = PropertiesUtils.getProperties(servletContext.getResourceAsStream(WEB_INF_CONFIG_PROPERTIES));

			if (properties != null) {

				// RapportoServerBL.connString =
				// properties.getProperty("connString");
				// RapportoServerBL.driverClass =
				// properties.getProperty("driverClass");
				// RapportoServerBL.userName =
				// properties.getProperty("userName");
				// RapportoServerBL.password =
				// properties.getProperty("password");

				String connString = properties.getProperty("connString");
				String driverClass = properties.getProperty("driverClass");
				String userName = properties.getProperty("userName");
				String password = properties.getProperty("password");

				jpaDbProps = new HashMap<String, String>();

				jpaDbProps.put("javax.persistence.jdbc.url", connString);
				jpaDbProps.put("javax.persistence.jdbc.driver", driverClass);
				jpaDbProps.put("javax.persistence.jdbc.user", userName);
				jpaDbProps.put("javax.persistence.jdbc.password", password);
			}
		} else {
			logger.warn("SoftwareConfig already inizialized");
		}
	}

}
