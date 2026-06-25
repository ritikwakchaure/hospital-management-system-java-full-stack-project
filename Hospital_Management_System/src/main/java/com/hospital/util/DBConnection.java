package com.hospital.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for obtaining MySQL database connections.
 */
public class DBConnection {

	// JDBC URL
	private static final String URL = "jdbc:mysql://localhost:3306/hospital_system";

	// Database credentials
	private static final String USERNAME = "USERNAME";
	private static final String PASSWORD = "PASSWORD";

	// Load MySQL 8 JDBC Driver
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Failed to load MySQL JDBC Driver", e);
		}
	}

	// Private constructor to prevent instantiation
	private DBConnection() {
	}

	/**
	 * Returns a new MySQL database connection.
	 *
	 * @return Connection
	 * @throws SQLException if a database access error occurs
	 */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}
}
