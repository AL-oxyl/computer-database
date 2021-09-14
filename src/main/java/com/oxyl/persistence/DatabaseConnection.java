package com.oxyl.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoadDriver {
	public LoadDriver() {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/computer-database-db?enabledTLSProtocols=TLSv1.2"
					+ "&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","admincdb","qwerty1234");
			
			System.out.println("ça marche");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQLException: " + e.getMessage());
		    System.out.println("SQLState: " + e.getSQLState());
		    System.out.println("VendorError: " + e.getErrorCode());
		}
 		
	}
	
}
	