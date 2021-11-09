package com.oxyl.mapper.bddmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.oxyl.model.Company;
import com.oxyl.model.Computer;

public class ComputerRowMapper implements RowMapper<Computer>{
	@Override
	public Computer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Company company = new Company(resultSet.getInt("computer.company_id"),resultSet.getString("company.name"));
		return new Computer.ComputerBuilder(resultSet.getString("computer.name"))
		           .id(resultSet.getInt("computer.id"))
		           .introductionDate(resultSet.getTimestamp("computer.introduced") != null ?
		            resultSet.getTimestamp("computer.introduced").toLocalDateTime().toLocalDate() : null)
		           .discontinuedDate(resultSet.getTimestamp("computer.discontinued") != null ?
		            resultSet.getTimestamp("computer.discontinued").toLocalDateTime().toLocalDate() : null)
		           .manufacturer(company).build();
	}
}
