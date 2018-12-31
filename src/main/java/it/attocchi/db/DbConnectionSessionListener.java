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

package it.attocchi.db;

import it.attocchi.utils.JdbcUtils;
import it.attocchi.web.config.SoftwareProperties;

import java.sql.Connection;
import java.util.Map;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

/**
 * Session Lifecycle Listener implementation class JPASessionListener
 *
 * @author Mirco Attocchi
 * @version $Id: $Id
 */
public class DbConnectionSessionListener implements HttpSessionListener {

	protected final Logger logger = Logger.getLogger(this.getClass().getName());
	/** Constant <code>SESSION_CONN="SESSION_CONN"</code> */
	public final static String SESSION_CONN = "SESSION_CONN";

	/**
	 * Default constructor.
	 */
	public DbConnectionSessionListener() {

	}

	/** {@inheritDoc} */
	public void sessionCreated(HttpSessionEvent e) {

		/*
		 * Aggiunta del Supporto alla Configurazione
		 */
		SoftwareProperties.init(e.getSession().getServletContext());
		Map<String, String> dbProps = SoftwareProperties.getJpaDbProps();

		Connection conn = null;
		if (dbProps != null) {
			// conn = new JdbcConnector(, , , ).getConnection();
			try {
				conn = JdbcUtils.getConnection(SoftwareProperties.driverClass, SoftwareProperties.connString, SoftwareProperties.userName, SoftwareProperties.password);
				// JdbcUtils.testConn(conn);
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
		e.getSession().setAttribute(SESSION_CONN, conn);
		logger.info(SESSION_CONN + " start");
	}

	/** {@inheritDoc} */
	public void sessionDestroyed(HttpSessionEvent e) {

		Connection conn = (Connection) e.getSession().getAttribute(SESSION_CONN);
		if (conn != null) {
			try {
				conn.close();
				logger.info(SESSION_CONN + " close");
			} catch (Exception ex) {
				logger.error(ex);
			}
		}

		//
		// chachedController.close();
	}

	// private static EntityManagerFactory emfShared;
	//
	// public static EntityManagerFactory getEntityManagerFactory() {
	// return emfShared;
	// }

}
