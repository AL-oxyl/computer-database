package com.oxyl.mapper.bddmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.oxyl.model.Company;

public class CompanyRowMapper implements RowMapper<Company> {
	@Override
	public Company mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		return new Company(resultSet.getInt("id"),resultSet.getString("name"));
	}
}
