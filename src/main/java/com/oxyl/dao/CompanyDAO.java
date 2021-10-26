package com.oxyl.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.dao.bddmapper.CompanyRowMapper;
import com.oxyl.model.Company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class CompanyDAO {
	/**
	 * This is the class that contains all companies. It follows Singleton pattern.
	 */
	private DataSource dsConnection;
	public static final short NUMBER_RESULT_BY_PAGE = 10;
	private static final String QUERY_ALL = "select id,name from company order by id";
	private static final String QUERY_GET_BY_ID = "select id,name from company where id= :id";
	private static final String QUERY_GET_BY_NAME = "select id,name from company where name= :name";
	private static final String QUERY_GET_RANGE = "select id,name from company order by id limit :size offset :number";
	private static final String QUERY_DELETE_COMPUTER_BY_COMPANY_ID = "delete from computer where company_id = :id";
	private static final String QUERY_DELETE_BY_ID = "delete from company where id = :id";
	private static final String QUERY_COUNT = "select count(company.id) from company";
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);
	
	@Autowired
	public CompanyDAO(DataSource dataSource) {
		LOGGER.info("instantiation company DAO");
		this.dsConnection = dataSource;
	}
	
	public List<Optional<Company>> getAllCompanies() {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		return getQueryHolder(QUERY_ALL, parameters);
	}
	
	public Optional<Company> getCompany(int id) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		return getQueryHolder(QUERY_GET_BY_ID, parameters).get(0);
	}
	
	public List<Optional<Company>> getCompany(String name) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("name", name);
		return getQueryHolder(QUERY_GET_BY_NAME, parameters);
	}
	
	public List<Optional<Company>> getCompanyRange(int pageNumber, int numberResultByPage) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("size", numberResultByPage);
		parameters.addValue("number", pageNumber);
		return getQueryHolder(QUERY_GET_RANGE, parameters);
	}
	
	private List<Optional<Company>> getQueryHolder(String queryName, MapSqlParameterSource parameters) {
	//	List<Company> companyList;
	/**	if(parameters.getValues().isEmpty()) {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dsConnection);
			companyList = jdbcTemplate.query(queryName, new CompanyRowMapper());
		} else {**/
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dsConnection);
		List<Company> companyList = jdbcTemplate.query(queryName, parameters, new CompanyRowMapper());
//		}
		return companyList.stream().map(Optional::ofNullable).collect(Collectors.toList());
	}
	
	@Transactional
	public void deleteCompanyById(int id) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dsConnection);
		parameters.addValue("id", id);
		jdbcTemplate.update(QUERY_DELETE_COMPUTER_BY_COMPANY_ID, parameters);
		jdbcTemplate.update(QUERY_DELETE_BY_ID, parameters);
	}
	
	public int getCompanyCount() {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dsConnection);
		return jdbcTemplate.queryForObject(QUERY_COUNT, parameters,Integer.class);
	}
	
	

}