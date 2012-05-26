package it.espressoft.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * 
 * @author Mirco Attocchi
 *
 */
public class JdbcUtils {

	protected static final Logger logger = Logger.getLogger(JdbcUtils.class.getName());

	/**
	 * Takes 3 parameters: databaseName, userName, password and connects to the
	 * database.
	 * 
	 * @param databaseName
	 *            holds database name,
	 * @param userName
	 *            holds user name
	 * @param password
	 *            holds password to connect the database,
	 * @return Returns the JDBC connection to the database
	 */
	public static Connection getConnection(String driverClass, String connString, String userName, String password) throws Exception {
		Connection conn = null;
		
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

		return conn;
	}
}
