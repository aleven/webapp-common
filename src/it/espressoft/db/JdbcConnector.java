package it.espressoft.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

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

			try {
				Class.forName(driverClass).newInstance();
			} catch (Exception ex) {
				logger.error("Check classpath. Cannot load db driver: " + driverClass, ex);
				throw ex;
			}

			try {
				conn = DriverManager.getConnection(connString, userName, password);
			} catch (SQLException ex) {
				logger.error("Driver loaded, but cannot connect to db: " + connString, ex);
				throw ex;
			}

		}
		return conn;
	}

	/**
	 * Close a Connection if not is passed in constructor.
	 * If you pass in constructor you close manualli, not with this method
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
}
