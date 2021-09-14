package com.oxyl.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	private static DatabaseConnection instance;
	public Connection connection;
	
	private DatabaseConnection() {
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/computer-database-db?enabledTLSProtocols=TLSv1.2"
					              + "&serverTimezone=UTC","admincdb","qwerty1234"); //&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false 
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		}		
	}

	public static DatabaseConnection getInstance() {
		if (instance == null) {
			instance = new DatabaseConnection();
		}
		return instance;
	}
}
	