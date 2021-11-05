package com.oxyl.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.oxyl.dao.CompanyDAO;
import com.oxyl.mapper.BddMapper;
import com.oxyl.model.Company;
import com.oxyl.ui.Pagination;

@Service
@Scope("prototype")
public class CompanyPageHandlerStrategyService implements GenericPageHandler<Company>{
	private final Long NUMBER_RESULT_BY_PAGE = 10L;
	private Long numberPage;
	private CompanyDAO companies;
	private Long pageIndex;
	private List<Company> companyPageList;
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyPageHandlerStrategyService.class);

	@Autowired
	public CompanyPageHandlerStrategyService(CompanyDAO companyDao) {
		this.companyPageList = BddMapper.companyPersistDtoListToCompanyModelList(
				companyDao.getCompanyRange(0L,NUMBER_RESULT_BY_PAGE));
		this.pageIndex = 0L;
		Long numberResult = companyDao.getCompanyCount();
		this.numberPage = (numberResult/NUMBER_RESULT_BY_PAGE)+1;
 	}

	public Long getPageIndex() {
		return pageIndex;
	}
	
	public void setPageIndex(Long index) {
		if(index < 0) {
			LOGGER.error("Index négatif interdit");
		} else if (index >= numberPage) {
			LOGGER.error("Index supérieur au nombre de page total");
		} else {
			this.pageIndex = index;
		}
	}


	public List<Company> getPageList() {
		return companyPageList;
	}

	public Long getNumberPage() {
		return numberPage;
	}	
	
	public void handlePage(int result) {
		switch (result) {
			case 1:
				updateInfo(pageIndex-1);
				break;
			case 2:
				updateInfo(pageIndex+1);
				break;
		}		
	}
	
	public void updateInfo(Long entry) {
		Long ref = pageIndex;
		setPageIndex(entry);
		if(ref != pageIndex) {
			this.companyPageList = BddMapper.companyPersistDtoListToCompanyModelList(
					companies.getCompanyRange(pageIndex,NUMBER_RESULT_BY_PAGE));
			LOGGER.info("Computer page info updated");
		}
	}
		
	public boolean testLeft() {
		if (pageIndex == 0) {
			return true;
		}
		return false;
	}
	

	public boolean testRight() {
		if (pageIndex == numberPage-1) {
			return true;
		}
		return false;
	}
	
	@Override
	public void setPageList(List<Company> pageList) {
		this.companyPageList = pageList;
	}
	
	public Pagination getPageState() {
		if (testLeft()) {
			return Pagination.LEFT;
		} else if (testRight()){
			return Pagination.RIGHT;
		}
		return Pagination.MIDDLE;
	}
}
