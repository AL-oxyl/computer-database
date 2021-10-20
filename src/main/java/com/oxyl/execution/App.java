package com.oxyl.execution;

import com.oxyl.controller.Form;
import com.oxyl.dao.CompanyDAO;
import com.oxyl.dao.ComputerDAO;
import com.oxyl.service.CompanyPageHandlerStrategyService;
import com.oxyl.service.ComputerPageHandlerStrategyService;
import com.oxyl.service.ComputerService;

//import com.oxyl.persistence.DatabaseConnection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main application
 *
 */
public class App {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ) {
    	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(WebExecution.class);
    	
    	Form form = context.getBean(Form.class);
        form.menu();
       	((ConfigurableApplicationContext) context).close();
		LOGGER.info("Le programme se ferme...");
    }
}
