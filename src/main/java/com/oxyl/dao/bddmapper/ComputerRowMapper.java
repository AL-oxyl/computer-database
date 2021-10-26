package com.oxyl.dao.bddmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import com.oxyl.mapper.BddMapper;
import com.oxyl.model.Company;
import com.oxyl.model.Computer;

public class ComputerRowMapper implements RowMapper<Computer>{
	@Override
	public Computer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Optional<Company> company = Optional.of(new Company(resultSet.getInt("computer.company_id"),resultSet.getString("company.name")));
		return new Computer.ComputerBuilder(resultSet.getString("computer.name"))
		           .id(resultSet.getInt("computer.id"))
		           .introductionDate(BddMapper.optTimestampToOptLocalDate(Optional.ofNullable(resultSet.getTimestamp("computer.introduced"))))
		           .discontinuedDate(BddMapper.optTimestampToOptLocalDate(Optional.ofNullable(resultSet.getTimestamp("computer.discontinued"))))
		           .manufacturer(company).build();
	}
}
