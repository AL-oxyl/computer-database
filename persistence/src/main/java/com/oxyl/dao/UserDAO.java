package com.oxyl.dao;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.dto.QUserPersistDto;
import com.oxyl.dto.UserPersistDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;



@Repository
public class UserDAO {
	/**
	 * This is the class that contains all companies. It follows Singleton pattern.
	 */
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDAO.class);
	private EntityManager entityManager;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserDAO(SessionFactory sessionFactory, PasswordEncoder passwordEncoder) {
		LOGGER.info("instantiation company DAO");
		this.entityManager = sessionFactory.createEntityManager();
		this.passwordEncoder = passwordEncoder;
	}
	
	public Optional<UserPersistDto> findByName(String name) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QUserPersistDto user = QUserPersistDto.userPersistDto;
		return Optional.ofNullable(queryFactory.selectFrom(user).where(user.username.eq(name)).fetchOne());
	}
}