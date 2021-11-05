package com.oxyl.controller;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.oxyl.model.Company;
import com.oxyl.model.Computer;
import com.oxyl.service.CompanyService;
import com.oxyl.service.ComputerPageHandlerStrategyService;
import com.oxyl.service.ComputerService;
import com.oxyl.validator.ComputerValidator;

@Controller
@RequestMapping(value  = "add")
public class ControllerAddComputer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAddComputer.class);
	private static List<Company> companies;
	private final String ERROR404 = "404";
	private final String ADD = "addComputer";
	private final String COMPUTER_NAME = "computerName";
	private final String INTRODUCED = "introduced";
	private final String DISCONTINUED = "discontinued";
	private final String COMPANY_ID = "companyId";

	
	private ComputerService computerService;
	private ComputerPageHandlerStrategyService pageService;
	private CompanyService companyService;

	@Autowired
	ControllerAddComputer(ComputerService computerService, ComputerPageHandlerStrategyService pageService, CompanyService companyService) {
		LOGGER.info("AddComputerController instantiated");
		this.computerService = computerService;
		this.pageService = pageService;
		this.companyService = companyService;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView doPost(@RequestParam(value = COMPUTER_NAME, required = true) String computerName,
			                      @RequestParam(value = INTRODUCED, required = false) String introduced,
			                      @RequestParam(value = DISCONTINUED, required = false) String discontinued,
			                      @RequestParam(value = COMPANY_ID, required = false) String companyId) {
		String computerId = "-1";
		String manufacturerName = "";
		
		if(ComputerValidator.checkNullableEntry(computerId, computerName,introduced,discontinued,companyId)) {
			return new ModelAndView(ERROR404);
		//	this.getServletContext().getRequestDispatcher(ERROR404).forward(req, resp);
		}
		ModelAndView addView = new ModelAndView(ADD);
		ComputerDTO dto = new ComputerDTO(computerId, computerName,introduced,discontinued,manufacturerName,companyId);
		Map<String, String> exceptionsMap = ComputerValidator.checkComputer(dto,companies);
		if(exceptionsMap.isEmpty()) {
			Computer computer = ComputerMapper.computerDTOToComputerModel(dto,companies);
			computerService.addComputer(computer);
			pageService.setNumberComputer(pageService.getNumberComputer()+1);
		} else {
			addView.addObject("computerName", computerName);
			addView.addObject("introduced", introduced);
			addView.addObject("discontinued", discontinued);
			addView.addObject("companyId", companyId);
		}
		addView.addObject("mapException",exceptionsMap);
		updateCompanies();
		addView.addObject("listCompanies", companies);
		return addView;
//		this.getServletContext().getRequestDispatcher(ADD).forward(req, resp);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	
	protected ModelAndView doGet(HttpServletRequest req ,HttpServletResponse resp) throws ServletException, IOException {
		updateCompanies();
		ModelAndView modelView = new ModelAndView(ADD);
		modelView.addObject("listCompanies", companies);
		return modelView;
	}
	
	private void updateCompanies() {
		companies = companyService.getCompanies();
	}
	
	public List<Company> getCompanies() {
		return companies;
	}
}
