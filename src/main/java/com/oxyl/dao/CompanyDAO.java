package com.oxyl.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.dto.CompanyPersistDto;
import com.oxyl.dto.QCompanyPersistDto;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository
public class CompanyDAO {
	/**
	 * This is the class that contains all companies. It follows Singleton pattern.
	 */
	public static final short NUMBER_RESULT_BY_PAGE = 10;
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);
	
	private EntityManager entityManager;
	
	
	@Autowired
	public CompanyDAO(SessionFactory sessionFactory) {
		LOGGER.info("instantiation company DAO");
		this.entityManager = sessionFactory.createEntityManager();
	}
	
	public List<CompanyPersistDto> getAllCompanies() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		return queryFactory.selectFrom(QCompanyPersistDto.companyPersistDto).fetch();
	}
	
	public Optional<CompanyPersistDto> findById(Integer id) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QCompanyPersistDto company = QCompanyPersistDto.companyPersistDto;
		return Optional.ofNullable(queryFactory.selectFrom(company).where(company.id.eq(id)).fetchOne());
	}
	
	public List<CompanyPersistDto> findByName(String name) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QCompanyPersistDto company = QCompanyPersistDto.companyPersistDto;
		return queryFactory.selectFrom(company).where(company.name.eq(name)).fetch();
	}
	
	public List<CompanyPersistDto> getCompanyRange(Long pageNumber, Long numberResultByPage) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QCompanyPersistDto company = QCompanyPersistDto.companyPersistDto;
		return queryFactory.selectFrom(company).offset(pageNumber).limit(numberResultByPage).fetch();
	}
	
	public Long getCompanyCount() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QCompanyPersistDto company = QCompanyPersistDto.companyPersistDto;
		return queryFactory.select(company.id.count()).from(company).fetchOne();	
	}
	
	@Transactional
	public void deleteCompanyById(int id) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QCompanyPersistDto company = QCompanyPersistDto.companyPersistDto;
		entityManager.getTransaction().begin();
		queryFactory.delete(company).where(company.id.eq(Integer.valueOf(id))).execute();
		entityManager.getTransaction().commit();

	}
}