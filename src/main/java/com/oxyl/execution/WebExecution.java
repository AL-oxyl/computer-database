package com.oxyl.execution;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"com.oxyl.controller",
	            "com.oxyl.dao",
	            "com.oxyl.mapper",
	            "com.oxyl.service",
	            "com.oxyl.validator",
	            "com.oxyl.ui"})
public class WebExecution extends AbstractContextLoaderInitializer {
	
	@Override
	protected WebApplicationContext createRootApplicationContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.register(WebExecution.class);
		return context;
	}
	
	@Bean
	public DataSource dataSource() {
		return new HikariDataSource(new HikariConfig("/datasource.properties"));
	}
	
	
}
