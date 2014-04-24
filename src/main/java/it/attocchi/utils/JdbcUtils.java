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

package it.attocchi.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;

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
	
	public static Timestamp convertToTimestamp(Date aJavaDate) {
		Timestamp retVal = null;

		if (aJavaDate != null) {
			retVal = new Timestamp(aJavaDate.getTime());
		}

		return retVal;
	}
	
	@Deprecated
	public static Date convertToDate(Timestamp aTimeStamp) {
		Date res = null;
		
		if (aTimeStamp != null) {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(new Date(aTimeStamp.getTime()));
			
			res = calendar.getTime();
		}
		
		return res; 
	}	
}
