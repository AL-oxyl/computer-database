package com.oxyl.controller;

import java.io.IOException;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.oxyl.dto.ComputerDTO;
import com.oxyl.mapper.ComputerMapper;
import com.oxyl.model.Company;
import com.oxyl.model.Computer;
import com.oxyl.service.CompanyService;
import com.oxyl.service.ComputerPageHandlerStrategyService;
import com.oxyl.service.ComputerService;
import com.oxyl.validator.ComputerValidator;

@Controller
@WebServlet("/add")
public class ControllerAddComputer extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAddComputer.class);
	private static List<Company> companies;
	private final String ERROR404 = "/WEB-INF/views/404.html";
	private final String ADD = "/WEB-INF/views/addComputer.jsp";
	@Autowired
	private ComputerService computerService;
	@Autowired
	private ComputerPageHandlerStrategyService pageService;


	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,config.getServletContext());
		super.init(config);
		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String computerId = "-1";
		String computerName = req.getParameter("computerName");
		String introductionDate = req.getParameter("introduced");
		String discontinuedDate = req.getParameter("discontinued");
		String manufacturerId = req.getParameter("companyId");
		String manufacturerName = "";
		
		if(ComputerValidator.checkNullableEntry(computerId, computerName,introductionDate,discontinuedDate,manufacturerId)) {
			this.getServletContext().getRequestDispatcher(ERROR404).forward(req, resp);
		}
		ComputerDTO dto = new ComputerDTO(computerId, computerName,introductionDate,discontinuedDate,manufacturerName,manufacturerId);
		Map<String, String> exceptionsMap = ComputerValidator.checkComputer(dto,companies);
		if(exceptionsMap.isEmpty()) {
			Computer computer = ComputerMapper.computerDTOToComputerModel(dto,companies);
			computerService.addComputer(computer);
			pageService.setNumberComputer(pageService.getNumberComputer()+1);
		}
		req.setAttribute("mapException",exceptionsMap);
		this.getServletContext().getRequestDispatcher(ADD).forward(req, resp);
	}
	
	protected void doGet(HttpServletRequest req ,HttpServletResponse resp) throws ServletException, IOException {
		updateCompanies();
		req.setAttribute("listCompanies", companies);
		this.getServletContext().getRequestDispatcher(ADD).forward(req, resp);
	}
	
	private static void updateCompanies() {
		companies = CompanyService.getCompanies();
	}
	
	public List<Company> getCompanies() {
		return companies;
	}
}
