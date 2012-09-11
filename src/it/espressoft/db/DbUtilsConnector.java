package it.espressoft.db;

import it.espressoft.utils.ListUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * 
 * @author Mirco Attocchi
 * 
 */
public class DbUtilsConnector extends JdbcConnector {

	public DbUtilsConnector(Connection conn) {
		super(conn);
	}

	public DbUtilsConnector(String connString, String driverClass, String userName, String password) {
		super(connString, driverClass, userName, password);
	}

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

	public <T> List<T> execute(boolean keepConnOpen, String aQuery, Class<T> clazz) throws Exception {
		List<T> result = new ArrayList<T>();

		// No DataSource so we must handle Connections manually
		QueryRunner run = new QueryRunner();

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

	private <T> ResultSetHandler<List<T>> getResultSetHandler(Class<T> clazz) {

		ResultSetHandler<List<T>> h = new BeanListHandler<T>(clazz);

		return h;
	}

	private <T> ResultSetHandler<T> getResultSetHandlerSingle(Class<T> clazz) {

		ResultSetHandler<T> h = new BeanHandler<T>(clazz);

		return h;
	}

}
