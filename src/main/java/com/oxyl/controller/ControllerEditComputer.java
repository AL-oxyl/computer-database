package com.oxyl.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.oxyl.exceptions.NotANumberException;
import com.oxyl.mapper.ComputerMapper;
import com.oxyl.model.Company;
import com.oxyl.model.Computer;
import com.oxyl.service.CompanyService;
import com.oxyl.service.ComputerPageHandlerStrategyService;
import com.oxyl.service.ComputerService;
import com.oxyl.service.State;
import com.oxyl.validator.ComputerValidator;

@Controller
@WebServlet("/edit")
public class ControllerEditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerEditComputer.class);
	private static List<Company> companies;
	final String VIEW_DASHBOARD = "/WEB-INF/views/dashboard.jsp";
	final String VIEW_404 = "/WEB-INF/views/404.html";
	final String VIEW_EDIT = "/WEB-INF/views/editComputer.jsp";
	@Autowired
	ComputerService computerService;	

	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,config.getServletContext());
		super.init(config);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		String computerId = req.getParameter("computerId");
		String computerName = req.getParameter("computerName");
		String introductionDate = req.getParameter("introduced");
		String discontinuedDate = req.getParameter("discontinued");
		String manufacturerId = req.getParameter("companyId");
		String manufacturerName = "";
		
		if(ComputerValidator.checkNullableEntry(computerId, computerName,introductionDate,discontinuedDate,manufacturerId)) {

			this.getServletContext().getRequestDispatcher(VIEW_404).forward(req, resp);
		} else {
			ComputerDTO computerDto = new ComputerDTO(computerId, computerName,introductionDate,discontinuedDate,manufacturerName,manufacturerId);
			Computer computer = ComputerMapper.computerDTOToComputerModel(computerDto,companies);
			if("".equals(computer.getComputerName())) {
				this.getServletContext().getRequestDispatcher(VIEW_404).forward(req, resp);
			} else {
				computerService.updateComputer(computer);
				resp.sendRedirect("dashboard?page=1");
			}	
		}
	}
	
	protected void doGet(HttpServletRequest req ,HttpServletResponse resp) throws ServletException, IOException {
		String computerId = req.getParameter("id");
		LOGGER.info("edit test");
		try {
			if (!ComputerValidator.checkNullableEntry(computerId) && ComputerValidator.checkValidId(computerId)) {
				updateCompanies();
				Optional<Computer> computer = computerService.getComputer(Integer.parseInt(computerId));
				if(computer.isPresent()) {
					ComputerDTO computerDto = new ComputerDTO(computer.get());
					req.setAttribute("listCompanies", companies);
					req.setAttribute("computer", computerDto);
					LOGGER.info("requete envoyee get edit");
					this.getServletContext().getRequestDispatcher(VIEW_EDIT).forward(req, resp);
				} else {
					resp.sendRedirect("dashboard?page=1");
				}
			} else {
				LOGGER.info("requete envoyee get edit - erreur 404");
				this.getServletContext().getRequestDispatcher(VIEW_404).forward(req, resp);
			}
		} catch (NotANumberException exception) {
			resp.sendRedirect("dashboard?page=1");
		}
	}
	
	private static void updateCompanies() {
		companies = CompanyService.getCompanies();
	}
	
	public List<Company> getCompanies() {
		return companies;
	}
}
