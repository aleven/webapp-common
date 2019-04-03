/*
    Copyright (c) 2012-2016 Mirco Attocchi
	
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.wrappers.StringTrimmedResultSet;

/**
 * <p>DbUtilsConnector class.</p>
 *
 * @author Mirco Attocchi
 * @version $Id: $Id
 */
public class DbUtilsConnector extends JdbcConnector {

	/**
	 * <p>Constructor for DbUtilsConnector.</p>
	 *
	 * @param conn a {@link java.sql.Connection} object.
	 */
	public DbUtilsConnector(Connection conn) {
		super(conn);
	}

	/**
	 * <p>Constructor for DbUtilsConnector.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 */
	public DbUtilsConnector(String url) {
		super(url);
	}

	/**
	 * <p>Constructor for DbUtilsConnector.</p>
	 *
	 * @param conn a {@link java.sql.Connection} object.
	 * @param connectionComeFromPool a boolean.
	 */
	public DbUtilsConnector(Connection conn, boolean connectionComeFromPool) {
		super(conn, connectionComeFromPool);
	}
	
	/**
	 * <p>Constructor for DbUtilsConnector.</p>
	 *
	 * @param connString a {@link java.lang.String} object.
	 * @param driverClass a {@link java.lang.String} object.
	 * @param userName a {@link java.lang.String} object.
	 * @param password a {@link java.lang.String} object.
	 */
	public DbUtilsConnector(String connString, String driverClass, String userName, String password) {
		super(connString, driverClass, userName, password);
	}

	/**
	 * <p>executeTop1.</p>
	 *
	 * @param keepConnOpen a boolean.
	 * @param aTop1Query a {@link java.lang.String} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public <T> T executeTop1(boolean keepConnOpen, String aTop1Query, Class<T> clazz) throws Exception {
		T result = null;

		// No DataSource so we must handle Connections manually
		QueryRunner run = new QueryRunner();

		try {

			/*
			 * Sembra che il like con i parametri ufficiali non funzioni, forse
			 * dovuto al fatto che son tutti object
			 */
			logger.debug("Esecuzione di : " + aTop1Query);
			List<T> listTop1 = run.query(getConnection(), aTop1Query, getResultSetHandler(clazz));

			if (listTop1.size() > 0) {
				logger.debug(String.format("Record Trovati: %s", listTop1.size()));
				result = listTop1.get(0);
			}

		} finally {
			// Use this helper method so we don't have to check for null
			if (!keepConnOpen)
				close();
		}

		return result;
	}

	/**
	 * <p>execute.</p>
	 *
	 * @param keepConnOpen a boolean.
	 * @param aQuery a {@link java.lang.String} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param params a {@link java.lang.Object} object.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T> List<T> execute(boolean keepConnOpen, String aQuery, Class<T> clazz, Object... params) throws Exception {
		List<T> result = new ArrayList<T>();

		// No DataSource so we must handle Connections manually
		QueryRunner run = new QueryRunner();

		try {

			/*
			 * Sembra che il like con i parametri ufficiali non funzioni, forse
			 * dovuto al fatto che son tutti object
			 */
			logger.debug(aQuery);
			result = run.query(getConnection(), aQuery, getResultSetHandler(clazz), params);

		} finally {
			if (!keepConnOpen)
				close();
		}

		return result;
	}

	/**
	 * <p>executeSingle.</p>
	 *
	 * @param keepConnOpen a boolean.
	 * @param aQuery a {@link java.lang.String} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param params a {@link java.lang.Object} object.
	 * @param <T> a T object.
	 * @return a T object.
	 * @throws java.lang.Exception if any.
	 */
	public <T> T executeSingle(boolean keepConnOpen, String aQuery, Class<T> clazz, Object... params) throws Exception {
		T result = null;

		// No DataSource so we must handle Connections manually
		QueryRunner run = new QueryRunner();

		try {

			logger.debug(aQuery);
			result = run.query(getConnection(), aQuery, getResultSetHandlerSingle(clazz), params);
		} catch (Exception ex) {
			logger.error("Error executeSingle", ex);
		} finally {
			if (!keepConnOpen)
				close();
		}

		return result;
	}

	/**
	 * <p>execute.</p>
	 *
	 * @param keepConnOpen a boolean.
	 * @param aQuery a {@link java.lang.String} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T> List<T> execute(boolean keepConnOpen, String aQuery, Class<T> clazz) throws Exception {
//		List<T> result = new ArrayList<T>();
//
//		// No DataSource so we must handle Connections manually
//		QueryRunner run = new QueryRunner();
//
//		try {
//
//			/*
//			 * Sembra che il like con i parametri ufficiali non funzioni, forse
//			 * dovuto al fatto che son tutti object
//			 */
//			logger.debug(aQuery);
//			result = run.query(getConnection(), aQuery, getResultSetHandler(clazz));
//
//		} finally {
//			if (!keepConnOpen)
//				close();
//		}
//
//		return result;
		return executeTrimedString(keepConnOpen, aQuery, clazz, null);
	}

	/**
	 * <p>executeTrimedString.</p>
	 *
	 * @param keepConnOpen a boolean.
	 * @param aQuery a {@link java.lang.String} object.
	 * @param clazz a {@link java.lang.Class} object.
	 * @param <T> a T object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public <T> List<T> executeTrimedString(boolean keepConnOpen, String aQuery, Class<T> clazz, Object... params) throws Exception {
		List<T> result = new ArrayList<T>();

		// No DataSource so we must handle Connections manually
		QueryRunner run = new QueryRunner() {
			protected ResultSet wrap(ResultSet rs) {
				return StringTrimmedResultSet.wrap(rs);
			}
		};

		try {

			/*
			 * Sembra che il like con i parametri ufficiali non funzioni, forse
			 * dovuto al fatto che son tutti object
			 */
			logger.debug(aQuery);
			result = run.query(getConnection(), aQuery, getResultSetHandler(clazz), params);

		} finally {
			if (!keepConnOpen)
				close();
		}

		return result;
	}

	public <T> List<T> executeTrimedString(boolean keepConnOpen, String aQuery, Class<T> clazz) throws Exception {
		List<T> result = new ArrayList<T>();

		// No DataSource so we must handle Connections manually
		QueryRunner run = new QueryRunner() {
			protected ResultSet wrap(ResultSet rs) {
				return StringTrimmedResultSet.wrap(rs);
			}
		};

		try {

			/*
			 * Sembra che il like con i parametri ufficiali non funzioni, forse
			 * dovuto al fatto che son tutti object
			 */
			logger.debug(aQuery);
			result = run.query(getConnection(), aQuery, getResultSetHandler(clazz));

		} finally {
			if (!keepConnOpen)
				close();
		}

		return result;
	}

	/**
	 * <p>executeMap.</p>
	 *
	 * @param keepConnOpen a boolean.
	 * @param aQuery a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public List<Map<String, Object>> executeMap(boolean keepConnOpen, String aQuery) throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		// No DataSource so we must handle Connections manually
		QueryRunner run = new QueryRunner();

		try {

			/*
			 * Sembra che il like con i parametri ufficiali non funzioni, forse
			 * dovuto al fatto che son tutti object
			 */
			logger.debug(aQuery);
			result = run.query(getConnection(), aQuery, new MapListHandler());

		} finally {
			if (!keepConnOpen)
				close();
		}

		return result;
	}

	/**
	 * <p>executeMapTrimmed.</p>
	 *
	 * @param keepConnOpen a boolean.
	 * @param aQuery a {@link java.lang.String} object.
	 * @return a {@link java.util.List} object.
	 * @throws java.lang.Exception if any.
	 */
	public List<Map<String, Object>> executeMapTrimmed(boolean keepConnOpen, String aQuery) throws Exception {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		// No DataSource so we must handle Connections manually
		QueryRunner run = new QueryRunner() {
			protected ResultSet wrap(ResultSet rs) {
				return StringTrimmedResultSet.wrap(rs);
			}
		};

		try {

			/*
			 * Sembra che il like con i parametri ufficiali non funzioni, forse
			 * dovuto al fatto che son tutti object
			 */
			logger.debug(aQuery);
			result = run.query(getConnection(), aQuery, new MapListHandler());

		} finally {
			if (!keepConnOpen)
				close();
		}

		return result;
	}
	
	private <T> ResultSetHandler<List<T>> getResultSetHandler(Class<T> clazz) {

		ResultSetHandler<List<T>> h = new BeanListHandler<T>(clazz);

		return h;
	}

	private <T> ResultSetHandler<T> getResultSetHandlerSingle(Class<T> clazz) {

		ResultSetHandler<T> h = new BeanHandler<T>(clazz);

		return h;
	}

	/**
	 * <p>close.</p>
	 *
	 * @param connector a {@link it.attocchi.db.DbUtilsConnector} object.
	 */
	public static void close(DbUtilsConnector connector) {
		if (connector != null) {
			connector.close();
		}
	}

}
