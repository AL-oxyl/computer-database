package com.oxyl.execution;

import com.oxyl.controller.Form;
import com.oxyl.dao.CompanyDAO;
import com.oxyl.persistence.DatabaseConnection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main application
 *
 */
public class App {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ) {
		DatabaseConnection db = DatabaseConnection.getInstance();
        try {
        	CompanyDAO.getInstance();
        	Form form = new Form();
        	form.menu();
			LOGGER.info("Le programme se ferme...");
			db.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
}
