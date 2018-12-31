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

package it.webappcommon.lib.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

/**
 * <p>DAOUtils class.</p>
 *
 * @author Mirco
 * @version $Id: $Id
 */
public class DAOUtils {

	private static Logger logger = Logger.getLogger(DAOUtils.class.getName());

	/**
	 * Quietly close the Connection. Any errors will be printed to the logger.
	 *
	 * @param connection
	 *            The Connection to be closed quietly.
	 */
	public static void close(Connection connection) {
		if (connection != null) {
			try {
				logger.debug("Closing connection");
				if (connection != null) {
					connection.close();
				}

				// try {
				// logger.info("Perform a System.gc");
				// System.gc();
				// } catch (Exception e) {
				// logger.error(e);
				// }

			} catch (SQLException e) {
				logger.error("Closing Connection failed: ", e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * Questo metodo e' utile per essere usato nei dao in cui viene fatto un
	 * metodo unico per le connessioni (e non due come una volta) In quel caso
	 * passo una variabile che dice se la connessione e' stata aperta dentro il
	 * metodo
	 *
	 * @param connection connessione
	 * @param close true se si vuole chiudere
	 */
	public static void close(Connection connection, boolean close) {
		if (close) {
			close(connection);
		} else {
			logger.debug("Closing connection: NO CLOSE");
		}
	}

	/**
	 * Quietly close the Statement. Any errors will be printed to the logger.
	 *
	 * @param statement
	 *            The Statement to be closed quietly.
	 */
	public static void close(Statement statement) {
		if (statement != null) {
			try {
				logger.debug("Closing statement");
				statement.close();
			} catch (SQLException e) {
				logger.error("Closing Statement failed: ", e);
				e.printStackTrace();
			}
		}
	}

	/**
	 * Quietly close the ResultSet. Any errors will be printed to the logger.
	 *
	 * @param resultSet
	 *            The ResultSet to be closed quietly.
	 */
	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				logger.debug("Closing resultSet");
				resultSet.close();
			} catch (SQLException e) {
				logger.error("Closing ResultSet failed: ", e);
				e.printStackTrace();
			}
		}
	}

//	/**
//	 * Quietly close the Connection and Statement. Any errors will be printed to
//	 * logger.
//	 *
//	 * @param connection
//	 *            The Connection to be closed quietly.
//	 * @param statement
//	 *            The Statement to be closed quietly.
//	 */
	// public static void close(Connection connection, Statement statement) {
	// close(statement);
	// close(connection);
	// }

	/**
	 * Quietly close the ResultSet and Statement. Any errors will be printed to
	 * the logger.
	 *
	 * @param statement
	 *            The Statement to be closed quietly.
	 * @param resultSet a {@link java.sql.ResultSet} object.
	 */
	public static void close(Statement statement, ResultSet resultSet) {
		close(resultSet);
		close(statement);
	}

	/**
	 * <p>close.</p>
	 *
	 * @param connection a {@link java.sql.Connection} object.
	 * @param statement a {@link java.sql.Statement} object.
	 * @param close a boolean.
	 */
	public static void close(Connection connection, Statement statement, boolean close) {
		close(statement);
		close(connection, close);
	}

//	/**
//	 * Quietly close the Connection, Statement and ResultSet. Any errors will be
//	 * printed to the logger.
//	 *
//	 * @param connection
//	 *            The Connection to be closed quietly.
//	 * @param statement
//	 *            The Statement to be closed quietly.
//	 * @param resultSet
//	 *            The ResultSet to be closed quietly.
//	 */
	// public static void close(Connection connection, Statement statement,
	// ResultSet resultSet) {
	// close(resultSet);
	// close(statement);
	// close(connection);
	// }

	/**
	 * Questo metodo e' utile per essere usato nei dao in cui viene fatto un
	 * metodo unico per le connessioni (e non due come una volta) In quel caso
	 * passo una variabile che dice se la connessione e' stata aperta dentro il
	 * metodo
	 * @param connection The Connection to be closed quietly
	 * @param statement The Statement to be closed quietly
	 * @param resultSet The ResultSet to be closed quietly
	 * @param close true se chiudere la connessione
	 */
	public static void close(Connection connection, Statement statement, ResultSet resultSet, boolean close) {
		close(resultSet);
		close(statement);
		close(connection, close);
	}
}
