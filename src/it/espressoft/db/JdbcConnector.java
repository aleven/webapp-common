package it.espressoft.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.log4j.Logger;

public class JdbcConnector<T> {

	private Connection conn;
	private boolean passedConnection = false;

	protected static final Logger logger = Logger.getLogger(JdbcConnector.class.getName());

//	private static final String QUERY_DISEGNO = "SELECT * " + "FROM " + "(" + "SELECT ntext2 AS nome, float1 AS revNum, SUBSTRING(CAST(ntext2 AS nvarchar(max)), LEN(CAST(ntext2 AS nvarchar(max)))-4, 1) AS revOnName FROM UserData " + "WHERE " + "(" + "ntext2 LIKE '%__.__.____.___._.dft' " + "OR " + "ntext2 LIKE '%__.__.____.___._-_.dft' " + ")" + "AND " + "nvarchar10 IN ('Rilasciato', 'En diffusion') " + ") AS dft " + "WHERE " + "nome LIKE '%{0}%' " + "ORDER BY revOnName DESC";
//	private static final String QUERY_DISEGNO_TOP1 = "SELECT TOP 1 * " + "FROM " + "(" + "SELECT ntext2 AS nome, float1 AS revNum, SUBSTRING(CAST(ntext2 AS nvarchar(max)), LEN(CAST(ntext2 AS nvarchar(max)))-4, 1) AS revOnName FROM UserData " + "WHERE " + "(" + "ntext2 LIKE '%__.__.____.___._.dft' " + "OR " + "ntext2 LIKE '%__.__.____.___._-_.dft' " + ")" + "AND " + "nvarchar10 IN ('Rilasciato', 'En diffusion') " + ") AS dft " + "WHERE " + "nome LIKE '%{0}%' " + "ORDER BY revOnName DESC";
//
//	private static final String QUERY_DISEGNO_ALL = "SELECT * " + "FROM " + "(" + "SELECT ntext2 AS nome, float1 AS revNum, SUBSTRING(CAST(ntext2 AS nvarchar(max)), LEN(CAST(ntext2 AS nvarchar(max)))-4, 1) AS revOnName FROM UserData " + "WHERE " + "(" + "ntext2 LIKE '%__.__.____.___._.dft' " + "OR " + "ntext2 LIKE '%__.__.____.___._-_.dft' " + ")" + "AND " + "nvarchar10 IN ('Rilasciato', 'En diffusion') " + ") AS dft " + "ORDER BY revOnName DESC";
//
//	private static final String QUERY_DISEGNO_TEST = "SELECT * " + "FROM " + "(" + "SELECT ntext2 AS nome, float1 AS revNum, SUBSTRING(CAST(ntext2 AS nvarchar(max)), LEN(CAST(ntext2 AS nvarchar(max)))-4, 1) AS revOnName FROM UserData " + "WHERE " + "(" + "ntext2 LIKE '%__.__.____.___._.dft' " + "OR " + "ntext2 LIKE '%__.__.____.___._-_.dft' " + ")" + "AND " + "nvarchar10 IN ('Rilasciato', 'En diffusion') " + ") AS dft " + "WHERE " + "nome LIKE '%28.63.0024.000.0%' " + "ORDER BY revOnName DESC";

//	public static final String pDB_CONN_STRING = "DB_CONN_STRING";
//	public static final String pDRIVER_CLASS_NAME = "DRIVER_CLASS_NAME";
//	public static final String pUSER_NAME = "USER_NAME";
//	public static final String pPASSWORD = "PASSWORD";
	
	protected String DB_CONN_STRING;
	protected String DRIVER_CLASS_NAME;
	protected String USER_NAME;
	protected String PASSWORD;

	// public InsightConnector() {
	// super();
	// }

	public JdbcConnector(String DB_CONN_STRING, String DRIVER_CLASS_NAME, String USER_NAME, String PASSWORD) {
		super();

		this.DB_CONN_STRING = DB_CONN_STRING;
		this.DRIVER_CLASS_NAME = DRIVER_CLASS_NAME;
		this.USER_NAME = USER_NAME;
		this.PASSWORD = PASSWORD;
	}

	/**
	 * Remember you have to close connection !!
	 * 
	 * @param conn
	 */
	public JdbcConnector(Connection conn) {
		this("","","","");

		this.conn = conn;
		passedConnection = true;
	}

//	/**
//	 * 
//	 * @param numeroDisegno
//	 * @return new ArrayList if no result
//	 * @throws SQLException
//	 */
//	public List<T> cercaDisegni(String numeroDisegno, Class<T> clazz) throws SQLException {
//		List<T> result = new ArrayList<T>();
//
//		// Create a ResultSetHandler implementation to convert the
//		// first row into an Object[].
//		// ResultSetHandler<Doc[]> h = getFiller();
//
//		// No DataSource so we must handle Connections manually
//		QueryRunner run = new QueryRunner();
//
//		// Connection conn = ;
//
//		try {
//			// Object[] result = run.query(conn,
//			// "SELECT * FROM Person WHERE name=?", h, "John Doe");
//
//			/*
//			 * Sembra che il like con i parametri ufficiali non funzioni, forse
//			 * dovuto al fatto che son tutti object
//			 */
//			result = run.query(getConnection(), formatLikeCSharp(QUERY_DISEGNO, numeroDisegno), getFiller(clazz));
//
//			// result = run.query(conn, QUERY_DISEGNO_TEST, getFiller());
//			// result = run.query(conn, QUERY_DISEGNO_ALL, getFiller());
//			// do something with the result
//
//		} finally {
//			// Use this helper method so we don't have to check for null
//			close();
//		}
//
//		return result;
//	}

	public T executeTop1(String aTop1Query, Class<T> clazz) throws SQLException {
		T result = null;

		// Create a ResultSetHandler implementation to convert the
		// first row into an Object[].
		// ResultSetHandler<Doc[]> h = getFiller();

		// No DataSource so we must handle Connections manually
		QueryRunner run = new QueryRunner();

		// Connection conn = ;

		try {
			// Object[] result = run.query(conn,
			// "SELECT * FROM Person WHERE name=?", h, "John Doe");

			/*
			 * Sembra che il like con i parametri ufficiali non funzioni, forse
			 * dovuto al fatto che son tutti object
			 */
			List<T> listTop1 = run.query(getConnection(), aTop1Query, getFiller(clazz));

			if (listTop1.size() > 0) {
				result = listTop1.get(0);
			}
			// result = run.query(conn, QUERY_DISEGNO_TEST, getFiller());
			// result = run.query(conn, QUERY_DISEGNO_ALL, getFiller());
			// do something with the result

		} finally {
			// Use this helper method so we don't have to check for null
			close();
		}

		return result;
	}

	public List<T> execute(String aQuery, Class<T> clazz) throws SQLException {
		List<T> result = new ArrayList<T>();

		// No DataSource so we must handle Connections manually
		QueryRunner run = new QueryRunner();

		try {

			/*
			 * Sembra che il like con i parametri ufficiali non funzioni, forse
			 * dovuto al fatto che son tutti object
			 */
			result = run.query(getConnection(), aQuery, getFiller(clazz));

		} finally {
			close();
		}

		return result;
	}

	/** Uses DriverManager. */
	private Connection getConnection() {
		// See your driver documentation for the proper format of this string :
		// String DB_CONN_STRING =
		// "jdbc:jtds:sqlserver://localhost:1433/STS_srvsql_1";
		// String DB_CONN_STRING =
		// "jdbc:sqlserver://assrv005\\cad2erp;databaseName=STS_srvsql_1";

		// String DB_CONN_STRING =
		// "jdbc:sqlserver://srvsql;databaseName=STS_srvsql_1";

		// Provided by your driver documentation. In this case, a MySql driver
		// is used :
		// String DRIVER_CLASS_NAME = "net.sourceforge.jtds.jdbc.Driver";

		// String DRIVER_CLASS_NAME =
		// "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		// String USER_NAME = "cad2erp";
		// String PASSWORD = "cad2erp";

		if (conn == null) {

			try {
				Class.forName(DRIVER_CLASS_NAME).newInstance();
			} catch (Exception ex) {
				logger.error("Check classpath. Cannot load db driver: " + DRIVER_CLASS_NAME);
			}

			try {
				conn = DriverManager.getConnection(DB_CONN_STRING, USER_NAME, PASSWORD);

			} catch (SQLException e) {
				logger.error("Driver loaded, but cannot connect to db: " + DB_CONN_STRING);
			}

		}
		return conn;
	}

	private ResultSetHandler<List<T>> getFiller(Class<T> clazz) {
		// ResultSetHandler<Doc[]> h = new ResultSetHandler<Object[]>() {
		// public Doc[] handle(ResultSet rs) throws SQLException {
		//
		// if (!rs.next()) {
		// return null;
		// }
		//
		// ResultSetMetaData meta = rs.getMetaData();
		// int cols = meta.getColumnCount();
		// Object[] result = new Object[cols];
		//
		// for (int i = 0; i < cols; i++) {
		// result[i] = rs.getObject(i + 1);
		// }
		//
		//
		//
		// return result;
		// }
		// };

		ResultSetHandler<List<T>> h = new BeanListHandler<T>(clazz);

		return h;
	}

	private String formatLikeCSharp(String aString, String param) {
		return aString.replace("{0}", param);
	}

	@Override
	protected void finalize() throws Throwable {
		close();
		super.finalize();
	}

	public void close() {
		if (!passedConnection) {
			try {
				DbUtils.close(conn);
			} catch (Exception ex) {
				logger.error(ex);
			}
		}
	}
}
