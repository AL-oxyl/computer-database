package com.oxyl.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {

	 private static DataSource instance;
	 private static HikariConfig config = new HikariConfig();
	 private HikariDataSource ds;
	 private static final Logger LOGGER = LoggerFactory.getLogger(DataSource.class);

	 
	 private DataSource() {
		 LOGGER.info("Nouvelle config");
		 config.setJdbcUrl( "jdbc:mysql://127.0.0.1:3306/computer-database-db?enabledTLSProtocols=TLSv1.2&serverTimezone=UTC" );
		 config.setUsername( "admincdb" );
		 config.setPassword( "qwerty1234" );
		 config.setDriverClassName("com.mysql.cj.jdbc.Driver");
		 config.addDataSourceProperty( "cachePrepStmts" , "true" );
		 config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
		 config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
	     this.ds = new HikariDataSource(config);  
	 }
	 
	 public static DataSource getInstance() {
		LOGGER.info("DataSource instantiée");
		if (instance==null) {
			instance = new DataSource();
		}
	 	return instance;
	 }

	 public Connection getConnection() throws SQLException {
	    return ds.getConnection();
	 }
}
