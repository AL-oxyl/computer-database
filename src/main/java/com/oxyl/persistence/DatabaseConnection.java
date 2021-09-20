package com.oxyl.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConnection {
	
	private static DatabaseConnection instance;
	public Connection connection;
	private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnection.class);
	
	private DatabaseConnection() {
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/computer-database-db?enabledTLSProtocols=TLSv1.2"
					              + "&serverTimezone=UTC","admincdb","qwerty1234"); //&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false 
		} catch (SQLException e) {
			LOGGER.error("Unable to open the connexion", e);
		}		
	}

	public static DatabaseConnection getInstance() {
		if (instance == null) {
			instance = new DatabaseConnection();
		}
		return instance;
	}
	
	public void close() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			LOGGER.error("Unable to close the connexion", e);

		}
		
	}
}
	