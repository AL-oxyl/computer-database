package com.oxyl.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.oxyl.dao.CompanyDAO;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

@RunWith(MockitoJUnitRunner.class)
public class CompanyPageHandlerStrategyServiceTest {
	
	@Mock
	private static CompanyDAO dao;

	@InjectMocks
	private CompanyPageHandlerStrategyService service;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public final void testBoundLeft() {
		service.updateInfo(1);
		Assert.assertEquals(service.getPageIndex(), 0);
	}
	
	@Test 
	public final void testBoundRight() {
		service.updateInfo(2);
		Assert.assertEquals(service.getPageIndex(), 1);
	}
	
	@Test
	public final void testIllegalSetRightPageValue() {
		service.setPageIndex(15);
		Assert.assertEquals(service.getPageIndex(), 0);
	}
	
	@Test
	public final void testIllegalSetLeftPageValue() {
		service.setPageIndex(-3);
		Assert.assertEquals(service.getPageIndex(), 0);
	}
	
	@Test
	public final void testLegalPageValue() {
		service.setPageIndex(4);
		Assert.assertEquals(service.getPageIndex(), 4);
	}
	
	@Test
	public final void testPageValue() {
		service.setPageIndex(15);
		Assert.assertEquals(service.getPageIndex(), 0);
	}
	
	@Test
	public final void testNumberPageValue() {
		int NUMBER_RESULT_BY_PAGE = 10;
		Mockito.when(dao.getCompanyCount()).thenReturn(42);
		Assert.assertEquals((dao.getCompanyCount()/NUMBER_RESULT_BY_PAGE)+1 , service.getNumberPage());
	}
	
	@After
	public final void testPositivePageIndex() {
		Assert.assertTrue(service.getPageIndex() >= 0);
	}
	
	@After
	public final void testInBoundPageIndex() {
		Assert.assertTrue(service.getPageIndex() < service.getNumberPage());
	}
	
}
