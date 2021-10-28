package com.oxyl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.oxyl.dto.ComputerDTO;
import com.oxyl.mapper.ComputerMapper;
import com.oxyl.service.ComputerPageHandlerStrategyService;
import com.oxyl.service.ComputerService;
import com.oxyl.service.State;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;

@Controller
@RequestMapping(value = "dashboard")
public class ControllerComputer {
	
	private ComputerPageHandlerStrategyService computerPaginationService;
	private ComputerService computerService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerComputer.class);
	private final String ERROR404 = "404";
	private final String SELECTION = "selection";
	private final String DASHBOARD = "dashboard";
	private final String SEARCH = "search";
	private final String PAGE = "page";
	private final String ORDER = "order";

	
	@Autowired
	ControllerComputer(ComputerService computerService, ComputerPageHandlerStrategyService computerPaginationService) {
		LOGGER.info("AddComputerController instantiated");
		this.computerService = computerService;
		this.computerPaginationService = computerPaginationService;
	}
	

	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView doPost(@RequestParam(value = SELECTION, required = true) String selection) throws ServletException, IOException {
		boolean isValid = true;
		if (selection.length() > 0) {
			isValid = deleteSelection(selection);
			if (!isValid) {
				return new ModelAndView(ERROR404);
			}
		}
		ModelAndView dashboardView = new ModelAndView(DASHBOARD);
		int numberPage = computerPaginationService.getPageIndex();
		updateOnId(numberPage);
		dashboardView = handleRequest(dashboardView);
		computerPaginationService.setPageChanged();		
		LOGGER.info("requete envoyee post dashboard");
		return dashboardView;
	}

	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView doGet(@RequestParam(value = SEARCH, required = false) String search,
                                 @RequestParam(value = PAGE, required = false) String page,
                                 @RequestParam(value = ORDER, required = false) String order) throws ServletException, IOException {
		if (page != null) {
			boolean isValid = updatePage(page);
			if (!isValid) {
				return new ModelAndView(ERROR404);
			}
		}
		ModelAndView dashboardView = new ModelAndView(DASHBOARD);
		if (search != null) {
			this.computerPaginationService.setSearchedEntry(search);
			this.computerPaginationService.changeState(State.SEARCH);
			if (page == null) {
				this.computerPaginationService.setLocalPageIndex(0);
			}
		}
		if (search == null && "1".equals(page)) {
			this.computerPaginationService.setSearchedEntry("");
			this.computerPaginationService.changeState(State.NORMAL);
		}
		LOGGER.info("requete envoyee get dashboard");
		return handleRequest(dashboardView);
	}

	private boolean updatePage(String page) {
		try {
			int index = Integer.parseInt(page) - 1;
			updateOnId(index);
			return true;
		} catch (NumberFormatException e) {
			updateOnId(0);
		} catch (NullPointerException e) {
			updateOnId(0);		}
		return false;
	}

	private void updateOnId(int id) {
		int lastPage = computerPaginationService.getPageIndex();
		computerPaginationService.setPageIndex(id);
		computerPaginationService.updateButtonArray(lastPage);
	}

	private boolean deleteSelection(String selection) {
		List<String> idSelected = Arrays.asList(selection.split(","));
		try {
			for (String id : idSelected) {
				computerService.deleteComputer(Integer.parseInt(id));
				computerPaginationService.setNumberComputer(computerPaginationService.getNumberComputer()-1);
			}
			return true;
		} catch (NumberFormatException e) {
			LOGGER.error("Array contains string not parsable");
		}
		return false;
	}

	private ModelAndView handleRequest(ModelAndView view) throws ServletException, IOException {
		List<ComputerDTO> computerList = ComputerMapper.computerListToDTOList(computerPaginationService.getComputerPageList());
		view.addObject("pages", computerPaginationService.getButtonArray());
		view.addObject("testLeft", !computerPaginationService.testLeft());
		view.addObject("testRight", !computerPaginationService.testRight());
		view.addObject("numberComputer", computerPaginationService.getLocalNumberComputer());
		view.addObject("numberPage", computerPaginationService.getLocalNumberPage());
		view.addObject("computerList", computerList);
		view.addObject("testNumber", computerPaginationService.getLocalNumberComputer() > 1);
		return view;
	}
}