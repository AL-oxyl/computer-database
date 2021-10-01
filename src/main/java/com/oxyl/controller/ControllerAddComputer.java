package com.oxyl.controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

@WebServlet("/add")
public class ControllerAddComputer extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAddComputer.class);
	private static List<Company> companies;

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
		if(computerName.equals(null) || 
				   introductionDate.equals(null) ||
				   discontinuedDate.equals(null) ||
				   manufacturerId.equals(null) ||
				   manufacturerName.equals(null) ||
				   computerId.equals(null)) {
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/404.html").forward(req, resp);
		}
		ComputerDTO dto = new ComputerDTO(computerId, computerName,introductionDate,discontinuedDate,manufacturerName,manufacturerId);
		System.out.println(dto.getComputerName());
		Computer computer = ComputerMapper.computerDTOToComputerModel(dto,companies);
		if("".equals(computer.getComputerName())) {
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/404.html").forward(req, resp);
		} else {
			ComputerService.addComputer(computer);
			this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(req, resp);
		}
	}
	
	protected void doGet(HttpServletRequest req ,HttpServletResponse resp) throws ServletException, IOException {
		updateCompanies();
		req.setAttribute("listCompanies", companies);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(req, resp);
	}
	
	private static void updateCompanies() {
		companies = CompanyService.getCompanies();
	}
	
	public List<Company> getCompanies() {
		return companies;
	}
}
