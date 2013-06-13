package it.attocchi.db;

import it.attocchi.utils.JdbcUtils;
import it.attocchi.utils.ListUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author Mirco Attocchi
 * 
 */
public class JdbcConnector {

	protected static final Logger logger = Logger.getLogger(JdbcConnector.class.getName());

	private Connection conn;
	private boolean passedConnection = false;

	protected String connString;
	protected String driverClass;
	protected String userName;
	protected String password;

	public JdbcConnector(String connString, String driverClass, String userName, String password) {
		super();

		this.connString = connString;
		this.driverClass = driverClass;
		this.userName = userName;
		this.password = password;
	}

	public JdbcConnector(Connection conn) {
		this("", "", "", "");

		this.conn = conn;
		passedConnection = true;
	}

	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	/**
	 * Uses DriverManager.
	 * 
	 * @throws Exception
	 */
	protected Connection getConnection() throws Exception {

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

			conn = JdbcUtils.getConnection(driverClass, connString, userName, password);

		}
		return conn;
	}

	/**
	 * Close a Connection if not is passed in constructor. If you pass in
	 * constructor you close manually, not with this method
	 */
	public void close() {
		if (!passedConnection) {
			try {
				// DbUtils.close(conn);
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
	}

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

	@Deprecated
	public boolean executeStored(boolean keepConnOpen, String storedName, Object... params) throws Exception {
		boolean res = false;

		try {

			String paramSign = StringUtils.repeat("?", ",", params.length);
			String query = "EXEC " + storedName + " " + paramSign;

			// res = getConnection().prepareStatement(aQuery).executeQuery();
			PreparedStatement ps = getConnection().prepareStatement(query);
			// ps.setEscapeProcessing(true);

			for (int i = 0; i < params.length; i++) {
				Object param = params[i];
				if (param instanceof String)
					ps.setString(i + 1, params[i].toString());
				else if (param instanceof Integer) {
					ps.setInt(i + 1, Integer.parseInt(params[i].toString()));
				} else if (param instanceof Double) {
					ps.setDouble(i + 1, Double.parseDouble(params[i].toString()));
				} else if (param instanceof Float) {
					ps.setDouble(i + 1, Float.parseFloat(params[i].toString()));
				}
			}

			logger.debug(ps);
			res = ps.execute();

		} finally {
			if (!keepConnOpen)
				close();
		}

		return res;
	}

	/**
	 * 
	 * @param aQuery
	 * @param params
	 * @return
	 * @throws SQLException
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

	/**
	 * 
	 * @param aQuery
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public int executeBatchUpdate(boolean keepConnOpen, List<String> batchQuery) throws Exception {
		int res = 0;

		try {

			if (ListUtils.isNotEmpty(batchQuery)) {

				logger.debug(batchQuery.size());

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
}
