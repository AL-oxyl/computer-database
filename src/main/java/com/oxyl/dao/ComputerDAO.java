package com.oxyl.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.dto.ComputerPersistDto;
import com.oxyl.dto.QCompanyPersistDto;
import com.oxyl.dto.QComputerPersistDto;
import com.oxyl.model.Company;
import com.oxyl.model.Computer;
import com.querydsl.jpa.impl.JPAQueryFactory;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;


@Repository
public class ComputerDAO  {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

	private EntityManager entityManager;

		
	@Autowired
	public ComputerDAO(SessionFactory sessionFactory) {
		LOGGER.info("instantiation computer DAO");
		this.entityManager = sessionFactory.createEntityManager();
	}
	
	public Optional<ComputerPersistDto> getComputer(int id) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QComputerPersistDto computer = QComputerPersistDto.computerPersistDto;
		return Optional.ofNullable(queryFactory.selectFrom(computer).where(computer.id.eq(id)).fetchOne());
	}
	
	public List<ComputerPersistDto> getAllComputers() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		return queryFactory.selectFrom(QComputerPersistDto.computerPersistDto).fetch();
	}
	
	public List<ComputerPersistDto> getComputerRange(Long pageNumber, Long numberResultByPage) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QComputerPersistDto computer = QComputerPersistDto.computerPersistDto;
		return queryFactory.selectFrom(computer).offset(pageNumber).limit(numberResultByPage).fetch();
	}
	
	public List<ComputerPersistDto> getSearchedComputerRange(Long pageNumber, Long numberResultByPage, String searchedName) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QComputerPersistDto computer = QComputerPersistDto.computerPersistDto;
		return queryFactory.selectFrom(computer).offset(pageNumber).limit(numberResultByPage).fetch();
	}
	
	public void insertComputer(Computer computer) throws EntityExistsException {
		entityManager.getTransaction().begin();
		entityManager.persist(computer);
		entityManager.getTransaction().commit();
	}
	
	public void updateComputer(Computer computer) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QComputerPersistDto computerDto = QComputerPersistDto.computerPersistDto;
		Company company = computer.getManufacturer();
		entityManager.getTransaction().begin();
		queryFactory.update(computerDto).where(computerDto.id.eq(Integer.valueOf(computer.getId())))
		            .set(computerDto.name, computer.getComputerName())
		            .set(computerDto.introduced, computer.getIntroductionDate())
		            .set(computerDto.discontinued, computer.getDiscontinuedDate())
		            .set(computerDto.id, company != null ? company.getId() : null ).execute();
		entityManager.getTransaction().commit();
	}
	
	public void deleteComputer(int id) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QComputerPersistDto computer = QComputerPersistDto.computerPersistDto;
		queryFactory.delete(computer).where(computer.id.eq(Integer.valueOf(id))).execute();
	}
	
	public Long getComputerCount() {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QComputerPersistDto computer = QComputerPersistDto.computerPersistDto;
		return queryFactory.select(computer.id.count()).from(computer).fetchOne();	
	}
	
	
	
	public Long getComputerCountSearch(String searchedName) {
		JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
		QComputerPersistDto computer = QComputerPersistDto.computerPersistDto;
		QCompanyPersistDto company = QCompanyPersistDto.companyPersistDto;
		return queryFactory.select(computer.count()).from(computer).leftJoin(computer.company,company)
		            .where(computer.name.like("%" + searchedName + "%")).fetchOne();
	}
}
