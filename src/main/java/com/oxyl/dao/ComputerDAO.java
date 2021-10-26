package com.oxyl.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.dao.bddmapper.ComputerRowMapper;
import com.oxyl.dto.BddComputerDTO;
import com.oxyl.model.Computer;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ComputerDAO implements ComputerDao {
	private static final String QUERY_ALL = "select computer.id,computer.name,company.name, computer.introduced,computer.discontinued,computer.company_id "
			                              + "from computer left join company on company.id = computer.company_id";
	private static final String QUERY_GET = QUERY_ALL + "where computer.id= :id";
	private static final String QUERY_INSERT = "insert INTO computer VALUES (NULL, :name, :intro, :discontinued, :company_id)";
	private static final String QUERY_UPDATE = "update computer SET name= :name, introduced= :intro, "
			                                 + "discontinued= :discontinued, company_id= :company_id where id= :id";
	private static final String QUERY_DELETE = "delete from computer where id= :id";
	private static final String RANGE = " limit :size offset :number";
	private static final String QUERY_GET_RANGE = QUERY_ALL + RANGE;
	private static final String QUERY_COUNT = "select count(computer.id) from computer";
	private static final String LEFT_JOIN_COMPUTER_COMPANY = " LEFT JOIN company ON computer.company_id = company.id";
	private static final String WHERE_COMPUTER_NAME = " where computer.name LIKE ?";
	private static final String WHERE_COMPANY_NAME = "company.name LIKE ?";
	private static final String QUERY_SEARCH_GET_RANGE = QUERY_ALL
														+ LEFT_JOIN_COMPUTER_COMPANY 
														+ WHERE_COMPUTER_NAME 
														+ " or " + WHERE_COMPANY_NAME 
														+ RANGE;
	private static final String QUERY_COUNT_SEARCH = QUERY_COUNT 
											+ LEFT_JOIN_COMPUTER_COMPANY 
											+ WHERE_COMPUTER_NAME 
											+ " or " + WHERE_COMPANY_NAME;
	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);
	
	private DataSource dsConnection;	
	
	@Autowired
	public ComputerDAO(DataSource dsConnection) {
		LOGGER.info("instantiation computer DAO");
		this.dsConnection = dsConnection;
	}
	
	private List<Optional<Computer>> getQueryHolder(String queryName, MapSqlParameterSource parameters) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dsConnection);
		List<Computer> computerList = jdbcTemplate.query(queryName, parameters, new ComputerRowMapper());
		return computerList.stream().map(Optional::ofNullable).collect(Collectors.toList());
	}
	
	private void computerModificationQueryHolder(String queryName, MapSqlParameterSource parameters) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dsConnection);
		jdbcTemplate.update(queryName, parameters);
	}
	
	private int countQueryHolder(String queryName, MapSqlParameterSource parameters) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dsConnection);
		return jdbcTemplate.queryForObject(queryName, parameters,Integer.class);
	}
	
	public Optional<Computer> getComputer(int id) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		return getQueryHolder(QUERY_GET, parameters).get(0);
	}
	
	public List<Optional<Computer>> getAllComputers() {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		return getQueryHolder(QUERY_ALL, parameters);
	}
	
	public List<Optional<Computer>> getComputerRange(int pageNumber, int numberResultByPage) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("size", numberResultByPage);
		parameters.addValue("number", pageNumber);
		return getQueryHolder(QUERY_GET_RANGE, parameters);
	}
	
	public List<Optional<Computer>> getSearchedComputerRange(int pageNumber, int numberResultByPage, String searchedName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("searchedName", "%"+searchedName+"%");	
		parameters.addValue("size", numberResultByPage);	
		parameters.addValue("number", pageNumber);
		return getQueryHolder(QUERY_SEARCH_GET_RANGE, parameters);
	}
	
	public void insertComputer(BddComputerDTO computerDto) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("name", computerDto.getComputerName().orElse(null));
		parameters.addValue("intro", computerDto.getIntroductionDate().orElse(null));	
		parameters.addValue("discontinued", computerDto.getDiscontinuedDate().orElse(null));
		parameters.addValue("company_id", computerDto.getManufacturerId().orElse(null));
		computerModificationQueryHolder(QUERY_INSERT, parameters);
	}
	
	public void updateComputer(BddComputerDTO computerDto) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", computerDto.getComputerId());
		parameters.addValue("name", computerDto.getComputerName().orElse(null));
		parameters.addValue("intro", computerDto.getIntroductionDate().orElse(null));	
		parameters.addValue("discontinued", computerDto.getDiscontinuedDate().orElse(null));
		parameters.addValue("company_id", computerDto.getManufacturerId().orElse(null));
		computerModificationQueryHolder(QUERY_UPDATE, parameters);
	}
	
	public void deleteComputer(int id) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		computerModificationQueryHolder(QUERY_DELETE, parameters);
	}
	
	public int getComputerCount() {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		return countQueryHolder(QUERY_COUNT, parameters);
	}
	
	public int getComputerCountSearch(String searchedName) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("searchedName", "%"+searchedName+"%");
		return countQueryHolder(QUERY_COUNT_SEARCH, parameters);
	}
}

