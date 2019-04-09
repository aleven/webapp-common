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

import it.attocchi.utils.JdbcUtils;
import it.attocchi.utils.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.sql.*;
import java.util.List;

/**
 * <p>JdbcConnector class.</p>
 *
 * @author Mirco Attocchi
 * @version $Id: $Id
 */
public class JdbcConnector implements Closeable {

	//	protected final Logger logger = Logger.getLogger(this.getClass().getName());
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Connection conn;
	private boolean passedConnection = false;

	protected String connString;
	protected String driverClass;
	protected String userName;
	protected String password;

	protected String url;

	/**
	 * <p>Constructor for JdbcConnector.</p>
	 *
	 * @param connString  a {@link java.lang.String} object.
	 * @param driverClass a {@link java.lang.String} object.
	 * @param userName    a {@link java.lang.String} object.
	 * @param password    a {@link java.lang.String} object.
	 */
	public JdbcConnector(String connString, String driverClass, String userName, String password) {
		super();

		this.connString = connString;
		this.driverClass = driverClass;
		this.userName = userName;
		this.password = password;
	}

	/**
	 * <p>Constructor for JdbcConnector.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 */
	public JdbcConnector(String url) {
		super();
		this.url = url;
	}

	/**
	 * <p>Constructor for JdbcConnector.</p>
	 *
	 * @param conn a {@link java.sql.Connection} object.
	 */
	public JdbcConnector(Connection conn) {
		this("", "", "", "");

		this.conn = conn;
		passedConnection = true;
		/*
		 * se la connessione viene passata di default impostiamo che sia
		 * lasciata aperta
		 */
		keepConnOpen = true;
	}

	/**
	 * Costruttore specificatamente pensato per utilizzo delle connessioni da
	 * pool
	 *
	 * @param conn                   la connessione istanziata esternamente
	 * @param connectionComeFromPool specifica se la connessione proviene da un pool, in questo
	 *                               caso non la tratta come una passata da non chiudere
	 */
	public JdbcConnector(Connection conn, boolean connectionComeFromPool) {
		this(conn);

		// se chiamo il close del connector così viene chiusa (e lasciata in
		// gestione al pool)
		passedConnection = !connectionComeFromPool;

		if (connectionComeFromPool) {
			logger.warn(this.getClass().getName() + " utilizza una connessione da un pool");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	/**
	 * Uses DriverManager.
	 *
	 * @return the connections
	 * @throws java.sql.SQLException if any.
	 */
	public Connection getConnection() throws SQLException {

		if (conn == null) {

			// try {
			// Class.forName(driverClass).newInstance();
			// } catch (Exception ex) {
			// logger.error("Check classpath. Cannot load db driver: " +
			// driverClass, ex);
			// throw ex;
			// }
			//
			// try {
			// conn = DriverManager.getConnection(connString, userName,
			// password);
			// } catch (SQLException ex) {
			// logger.error("Driver loaded, but cannot connect to db: " +
			// connString, ex);
			// throw ex;
			// }

			if (url != null) {
				conn = DriverManager.getConnection(url);
			} else {
				conn = JdbcUtils.getConnection(driverClass, connString, userName, password);
			}
		}
		return conn;
	}

	/**
	 * <p>reopenConnection.</p>
	 */
	public void reopenConnection() {
		conn = null;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Close a Connection if not is passed in constructor. If you pass in
	 * constructor you close manually, not with this method
	 */
	@Override
	public void close() {
		if (!passedConnection) {
			try {
				// DbUtils.close(conn);
				if (conn != null && !conn.isClosed()) {
					conn.close();
					conn = null;
				}
			} catch (Exception ex) {
				logger.error("close", ex);
			}
		}
	}

	/**
	 * <p>isClosed.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isClosed() {
		boolean res = true;
		try {
			res = conn != null && conn.isClosed();
		} catch (Exception ex) {
			logger.error("isClosed", ex);
		}
		return res;
	}

	/**
	 * <p>executeSelect.</p>
	 *
	 * @param keepConnOpen a boolean.
	 * @param aQuery       a {@link java.lang.String} object.
	 * @return a {@link java.sql.ResultSet} object.
	 * @throws java.lang.Exception if any.
	 */
	public ResultSet executeSelect(boolean keepConnOpen, String aQuery) throws Exception {
		ResultSet res = null;

		try {

			logger.debug(aQuery);

			res = getConnection().prepareStatement(aQuery).executeQuery();

		} finally {
			if (!keepConnOpen)
				close();
		}

		return res;
	}

	/**
	 * execute a query with secure params
	 *
	 * @param keepConnOpen boolean keep connection open after execute query
	 * @param aQuery       the SQL to execute
	 * @param params       list of params
	 * @return the resultset
	 * @throws Exception
	 */
	public ResultSet execute(boolean keepConnOpen, String aQuery, Object... params) throws Exception {
		ResultSet res = null;
		try {
			logger.debug(aQuery);
			final PreparedStatement ps = getConnection().prepareStatement(aQuery);
			setParams(ps, params);
			res = ps.executeQuery();
		} finally {
			if (!keepConnOpen)
				close();
		}
		return res;
	}

	/**
	 * <p>execute.</p>
	 *
	 * @param keepConnOpen a boolean.
	 * @param sqlCommand   a {@link java.lang.String} object.
	 * @return a boolean.
	 * @throws java.lang.Exception if any.
	 */
	public boolean execute(boolean keepConnOpen, String sqlCommand) throws Exception {
		boolean res = false;

		try {

			logger.debug(sqlCommand);
			res = getConnection().prepareStatement(sqlCommand).execute();

			// Non Ritorna True in caso di Esecuzione di Stored
			res = true;

		} finally {
			if (!keepConnOpen)
				close();
		}

		return res;
	}

	/**
	 * <p>executeStored.</p>
	 *
	 * @param keepConnOpen a boolean.
	 * @param storedName   a {@link java.lang.String} object.
	 * @param params       a {@link java.lang.Object} object.
	 * @return a boolean.
	 * @throws java.lang.Exception if any.
	 */
	@Deprecated
	public boolean executeStored(boolean keepConnOpen, String storedName, Object... params) throws Exception {
		boolean res = false;

		try {

			String paramSign = StringUtils.repeat("?", ",", params.length);
			String query = "EXEC " + storedName + " " + paramSign;

			// res = getConnection().prepareStatement(aQuery).executeQuery();
			PreparedStatement ps = getConnection().prepareStatement(query);
			// ps.setEscapeProcessing(true);

//			for (int i = 0; i < params.length; i++) {
//				Object param = params[i];
//				if (param instanceof String)
//					ps.setString(i + 1, params[i].toString());
//				else if (param instanceof Integer) {
//					ps.setInt(i + 1, Integer.parseInt(params[i].toString()));
//				} else if (param instanceof Double) {
//					ps.setDouble(i + 1, Double.parseDouble(params[i].toString()));
//				} else if (param instanceof Float) {
//					ps.setDouble(i + 1, Float.parseFloat(params[i].toString()));
//				}
//			}

			setParams(ps, params);

			logger.debug("executeStored", ps);
			res = ps.execute();

		} finally {
			if (!keepConnOpen)
				close();
		}

		return res;
	}

	/**
	 * <p>executeUpdate.</p>
	 *
	 * @param keepConnOpen keep connection open
	 * @param aQuery       the SQL query
	 * @return row count
	 * @throws SQLException        sql exception
	 * @throws java.lang.Exception if any.
	 */
	public int executeUpdate(boolean keepConnOpen, String aQuery) throws Exception {
		int res = 0;
		try {
			logger.debug(aQuery);
			res = getConnection().prepareStatement(aQuery).executeUpdate();
		} finally {
			if (!keepConnOpen)
				close();
		}
		return res;
	}

	public int executeUpdate(boolean keepConnOpen, String aQuery, Object... params) throws Exception {
		int res = 0;
		try {
			logger.debug(aQuery);
			final PreparedStatement ps = getConnection().prepareStatement(aQuery);
			setParams(ps, params);
			res = ps.executeUpdate();
		} finally {
			if (!keepConnOpen)
				close();
		}
		return res;
	}

	/**
	 * <p>executeBatchUpdate.</p>
	 *
	 * @param keepConnOpen keep connection open
	 * @param batchQuery   list of SQL queries
	 * @return row count
	 * @throws SQLException        sql exception
	 * @throws java.lang.Exception if any.
	 */
	public int executeBatchUpdate(boolean keepConnOpen, List<String> batchQuery) throws Exception {
		int res = 0;

		try {

			if (ListUtils.isNotEmpty(batchQuery)) {

				logger.debug("executeBatchUpdate", batchQuery.size());

				Connection connection = getConnection();

				// connection.setAutoCommit(false);

				Statement statement = connection.createStatement();
				for (String aQuery : batchQuery) {
					statement.addBatch(aQuery);
				}

				int[] counts = statement.executeBatch();

				for (int count : counts) {
					res = res + count;
				}
				// connection.commit();
			}

		} finally {
			if (!keepConnOpen)
				close();
		}

		return res;
	}

	/**
	 * utilizzato da chi estende questa classe, quando estentendo c'e' un metodo
	 * che esegue piu query in sequenza
	 */
	protected boolean keepConnOpen = false;

	/**
	 * <p>keepConnOpen.</p>
	 */
	public void keepConnOpen() {
		keepConnOpen = true;
	}

	private void setParams(PreparedStatement ps, Object... params) throws SQLException {
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				final int paramIndex = i + 1;
				final Object paramValue = params[i];
				if (paramValue instanceof String)
					ps.setString(paramIndex, paramValue.toString());
				else if (paramValue instanceof Integer)
					ps.setInt(paramIndex, Integer.parseInt(paramValue.toString()));
				else if (paramValue instanceof Long)
					ps.setLong(paramIndex, Long.parseLong(paramValue.toString()));
				else if (paramValue instanceof Double)
					ps.setDouble(paramIndex, Double.parseDouble(paramValue.toString()));
				else if (paramValue instanceof Float)
					ps.setFloat(paramIndex, Float.parseFloat(paramValue.toString()));
			}
		}
	}
}
