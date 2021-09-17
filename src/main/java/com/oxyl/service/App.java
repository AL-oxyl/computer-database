package com.oxyl.service;

import com.oxyl.controller.Form;
import com.oxyl.dao.CompanyDAO;
import com.oxyl.persistence.DatabaseConnection;
import java.sql.SQLException;

/**
 * Main application
 *
 */
public class App 
{
    public static void main( String[] args ) {
		DatabaseConnection db = DatabaseConnection.getInstance();
        try {
        	CompanyDAO.getInstance();
        	Form form = new Form();
        	form.menu();
			System.out.println("Le programme se ferme...");
			db.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
    }
}
