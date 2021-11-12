package com.oxyl.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oxyl.dao.UserDAO;
import com.oxyl.dto.UserPersistDto;
import com.oxyl.mapper.frontmapper.BddMapper;
import com.oxyl.model.CustomUserDetails;
import com.oxyl.model.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserDAO userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserPersistDto> userDto = userDao.findByName(username);
		Optional<User> user = BddMapper.userPersistDtoToUserModel(userDto);
		user.orElseThrow(() -> new UsernameNotFoundException("Username not found : " + username));
		return user.map(CustomUserDetails::new).get();
	}
}
 