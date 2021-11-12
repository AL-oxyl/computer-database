package com.oxyl.mapper.bddmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.oxyl.model.User;

public class UserRowMapper implements RowMapper<User>{
	@Override
	public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		return new User.UserBuilder().id(resultSet.getInt("id"))
				                     .username(resultSet.getString("username"))
				                     .password(resultSet.getString("password"))
				                     .role(resultSet.getString("role"))
				                     .enabled(resultSet.getBoolean("enabled")).build();
	}
}
