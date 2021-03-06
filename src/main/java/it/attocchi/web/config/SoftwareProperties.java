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

package it.attocchi.web.config;

import it.attocchi.utils.PropertiesUtils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>SoftwareProperties class.</p>
 *
 * @author mirco
 * @version $Id: $Id
 */
public class SoftwareProperties {

	/** Constant <code>logger</code> */
	protected static final Logger logger = LoggerFactory.getLogger(SoftwareProperties.class.getName());

	/** Constant <code>WEB_INF_CONFIG_PROPERTIES="/WEB-INF/software.properties"</code> */
	public static final String WEB_INF_CONFIG_PROPERTIES = "/WEB-INF/software.properties";
	// public static final String PU_NAME = "AtreeFLOW-WS";
	/** Constant <code>PU_NAME="DEFAULT_PU"</code> */
	public static final String PU_NAME = "DEFAULT_PU";

	/** Constant <code>PROPERTY_connString="connString"</code> */
	public static final String PROPERTY_connString = "connString";
	/** Constant <code>PROPERTY_driverClass="driverClass"</code> */
	public static final String PROPERTY_driverClass = "driverClass";
	/** Constant <code>PROPERTY_userName="userName"</code> */
	public static final String PROPERTY_userName = "userName";
	/** Constant <code>PROPERTY_password="password"</code> */
	public static final String PROPERTY_password = "password";

	private static Properties properties;

	/**
	 * <p>Getter for the field <code>properties</code>.</p>
	 *
	 * @return a {@link java.util.Properties} object.
	 */
	public static Properties getProperties() {
		return properties;
	}

	/**
	 * <p>Setter for the field <code>properties</code>.</p>
	 *
	 * @param properties a {@link java.util.Properties} object.
	 */
	public static void setProperties(Properties properties) {
		SoftwareProperties.properties = properties;
	}

	private static Map<String, String> jpaDbProps;

	/**
	 * <p>Getter for the field <code>jpaDbProps</code>.</p>
	 *
	 * @return a {@link java.util.Map} object.
	 */
	public static Map<String, String> getJpaDbProps() {
		return jpaDbProps;
	}

	/**
	 * <p>Setter for the field <code>jpaDbProps</code>.</p>
	 *
	 * @param dbProps a {@link java.util.Map} object.
	 */
	public static void setJpaDbProps(Map<String, String> dbProps) {
		SoftwareProperties.jpaDbProps = dbProps;
	}

	/**
	 * Inizializza i Valori della BL e della PersistenceUnit
	 *
	 * @param servletContext a {@link javax.servlet.ServletContext} object.
	 */
	public static void init(ServletContext servletContext) {

		if (jpaDbProps == null) {	

			InputStream fileProperties = servletContext.getResourceAsStream(WEB_INF_CONFIG_PROPERTIES);
			if (fileProperties != null) {
				
				logger.warn("Loading " + WEB_INF_CONFIG_PROPERTIES);
				properties = PropertiesUtils.getProperties(fileProperties);

				if (properties != null) {

					// RapportoServerBL.connString =
					// properties.getProperty("connString");
					// RapportoServerBL.driverClass =
					// properties.getProperty("driverClass");
					// RapportoServerBL.userName =
					// properties.getProperty("userName");
					// RapportoServerBL.password =
					// properties.getProperty("password");

					connString = StringUtils.trim(properties.getProperty(PROPERTY_connString));
					driverClass = StringUtils.trim(properties.getProperty(PROPERTY_driverClass));
					userName = StringUtils.trim(properties.getProperty(PROPERTY_userName));
					password = StringUtils.trim(properties.getProperty(PROPERTY_password));

					jpaDbProps = new HashMap<String, String>();

					jpaDbProps.put("javax.persistence.jdbc.url", connString);
					jpaDbProps.put("javax.persistence.jdbc.driver", driverClass);
					jpaDbProps.put("javax.persistence.jdbc.user", userName);
					jpaDbProps.put("javax.persistence.jdbc.password", password);

					logger.debug("javax.persistence.jdbc.url={}", connString);
					logger.debug("javax.persistence.jdbc.driver={}", driverClass);
					logger.debug("javax.persistence.jdbc.user={}", userName);
					logger.debug("javax.persistence.jdbc.password={}", StringUtils.repeat('*', StringUtils.defaultString(password).length()));
										
					/*
					 * Se uno dei Parametri da File e' nullo annullo tutti gli
					 * oggetti
					 */
					if (StringUtils.isEmpty(connString) || StringUtils.isEmpty(driverClass)) {
						properties = null;
						jpaDbProps = null;

						logger.warn("SoftwareConfig loaded but Empty Database Configuration");
					}
				}
			}
		} else {
			logger.warn("SoftwareConfig already inizialized");
		}
	}

	/** Constant <code>connString</code> */
	public static String connString;
	/** Constant <code>driverClass</code> */
	public static String driverClass;
	/** Constant <code>userName</code> */
	public static String userName;
	/** Constant <code>password</code> */
	public static String password;
}
