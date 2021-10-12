package com.oxyl.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.oxyl.service.State;
import com.oxyl.validator.ComputerValidator;

@WebServlet("/edit")
public class ControllerEditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerEditComputer.class);
	private static List<Company> companies;
	final String VIEW_DASHBOARD = "/WEB-INF/views/dashboard.jsp";
	final String VIEW_404 = "/WEB-INF/views/404.html";
	final String VIEW_EDIT = "/WEB-INF/views/editComputer.jsp";

	public ControllerEditComputer() {
		super();
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		String computerId = req.getParameter("computerId");
		String computerName = req.getParameter("computerName");
		String introductionDate = req.getParameter("introduced");
		String discontinuedDate = req.getParameter("discontinued");
		String manufacturerId = req.getParameter("companyId");
		String manufacturerName = "";
		
		if(ComputerValidator.checkNullableEntry(computerId, computerName,introductionDate,discontinuedDate,manufacturerId)) {
			LOGGER.info("Etat introductionDate :" +  introductionDate);
			LOGGER.info("Etat discontinuedDate :" +  discontinuedDate);
			LOGGER.info("Etat manufacturerId :" +  manufacturerId);
			LOGGER.info("Etat manufacturerName :" +  manufacturerName);
			LOGGER.info("Etat computerId :" +  computerId);
			LOGGER.info("Etat computerName :" + computerName);
			this.getServletContext().getRequestDispatcher(VIEW_404).forward(req, resp);
		} else {
			ComputerDTO computerDto = new ComputerDTO(computerId, computerName,introductionDate,discontinuedDate,manufacturerName,manufacturerId);
			Computer computer = ComputerMapper.computerDTOToComputerModel(computerDto,companies);
			if("".equals(computer.getComputerName())) {
				this.getServletContext().getRequestDispatcher(VIEW_404).forward(req, resp);
			} else {
				ComputerService.updateComputer(computer);
				resp.sendRedirect("dashboard?page=1");
			}	
		}
	}
	
	protected void doGet(HttpServletRequest req ,HttpServletResponse resp) throws ServletException, IOException {
		String computerId = req.getParameter("id");
		if (computerId != null) {
			updateCompanies();
			req.setAttribute("listCompanies", companies);
			req.setAttribute("computerId", computerId);
			LOGGER.info("requete envoyee get edit");
			this.getServletContext().getRequestDispatcher(VIEW_EDIT).forward(req, resp);
		} else {
			LOGGER.info("requete envoyee get edit - erreur 404");
			this.getServletContext().getRequestDispatcher(VIEW_404).forward(req, resp);
		}
	}
	
	private static void updateCompanies() {
		companies = CompanyService.getCompanies();
	}
	
	public List<Company> getCompanies() {
		return companies;
	}
}
