package com.oxyl.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.dto.ComputerDTO;
import com.oxyl.mapper.ComputerMapper;
import com.oxyl.model.Company;
import com.oxyl.model.Computer;
import com.oxyl.service.CompanyService;
import com.oxyl.service.ComputerService;
import com.oxyl.validator.ComputerValidator;

@WebServlet("/add")
public class ControllerAddComputer extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAddComputer.class);
	private static List<Company> companies;
	private final String ERROR404 = "/WEB-INF/views/404.html";
	private final String ADD = "/WEB-INF/views/addComputer.jsp";


	public ControllerAddComputer() {
		super();
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
		System.out.println(dto.getComputerName());
		Computer computer = ComputerMapper.computerDTOToComputerModel(dto,companies);
		if("".equals(computer.getComputerName())) {
			this.getServletContext().getRequestDispatcher(ERROR404).forward(req, resp);
		} else {
			ComputerService.addComputer(computer);
			this.getServletContext().getRequestDispatcher(ADD).forward(req, resp);
		}
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
