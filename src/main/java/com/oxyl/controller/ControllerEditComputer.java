package com.oxyl.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.oxyl.dto.ComputerDTO;
import com.oxyl.exceptions.NotANumberException;
import com.oxyl.mapper.CompanyMapper;
import com.oxyl.mapper.ComputerMapper;
import com.oxyl.model.Company;
import com.oxyl.model.Computer;
import com.oxyl.service.CompanyService;
import com.oxyl.service.ComputerService;
import com.oxyl.validator.ComputerValidator;

@Controller
@RequestMapping(value = "edit")
public class ControllerEditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerEditComputer.class);
	private List<Company> companies;
	final String VIEW_DASHBOARD = "dashboard";
	final String ERROR_404 = "404";
	final String VIEW_EDIT = "editComputer";
	
	private final String COMPUTER_NAME = "computerName";
	private final String INTRODUCED = "introduced";
	private final String DISCONTINUED = "discontinued";
	private final String COMPANY_ID = "companyId";
	private final String COMPUTER_ID = "computerId";
	private final String ID = "id";

	ComputerService computerService;
	CompanyService companyService;

	@Autowired
	ControllerEditComputer(ComputerService computerService, CompanyService companyService) {
		this.companyService = companyService;
		this.computerService = computerService;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	protected ModelAndView doPut(
			@RequestParam(value = COMPUTER_ID, required = true) String computerId,
			@RequestParam(value = COMPUTER_NAME, required = true) String computerName,
            @RequestParam(value = INTRODUCED, required = false) String introductionDate,
            @RequestParam(value = DISCONTINUED, required = false) String discontinuedDate,
            @RequestParam(value = COMPANY_ID, required = false) String manufacturerId) throws ServletException, IOException {	
		String manufacturerName = "";
		
		if(ComputerValidator.checkNullableEntry(computerId, computerName,introductionDate,discontinuedDate,manufacturerId)) 
			return new ModelAndView(ERROR_404);
		ComputerDTO computerDto = new ComputerDTO(computerId, computerName,introductionDate,discontinuedDate,manufacturerName,manufacturerId);
		Computer computer = ComputerMapper.computerDTOToComputerModel(computerDto,companies);
		if("".equals(computer.getComputerName())) 
			return new ModelAndView(ERROR_404);
		computerService.updateComputer(computer);
		return new ModelAndView(VIEW_DASHBOARD).addObject("page", "1");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView doGet(@RequestParam(value = ID, required = true) String computerId) throws ServletException, IOException {
		LOGGER.info("edit test");
		ModelAndView editView = new ModelAndView(VIEW_EDIT);
		try {
			if (!ComputerValidator.checkNullableEntry(computerId) && ComputerValidator.checkValidId(computerId)) {
				updateCompanies(); 
				Optional<Computer> computer = computerService.getComputer(Integer.parseInt(computerId));
				if(computer.isPresent()) {
					ComputerDTO computerDto = new ComputerDTO(computer.get());
					editView.addObject("listCompanies", companies);
					editView.addObject("computer", computerDto);
					LOGGER.info("requete envoyee get edit");
					return editView;
				} else {
					return new ModelAndView(VIEW_DASHBOARD).addObject("page", "1");
				}
			
			} else {
				LOGGER.info("requete envoyee get edit - erreur 404");
				return new ModelAndView(ERROR_404);
			}
		} catch (NumberFormatException | NotANumberException exception) {
			return new ModelAndView(VIEW_DASHBOARD).addObject("page", "1");
		}
	}
	
	private void updateCompanies() {
		companies = CompanyMapper.companyListToModelList(companyService.getCompanies());
	}
	
	public List<Company> getCompanies() {
		return companies;
	}
}
