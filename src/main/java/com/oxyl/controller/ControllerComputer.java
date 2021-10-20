package com.oxyl.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.oxyl.mapper.ComputerMapper;
import com.oxyl.model.Computer;
import com.oxyl.service.ComputerPageHandlerStrategyService;
import com.oxyl.service.ComputerService;
import com.oxyl.service.State;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@Controller
@WebServlet("/dashboard")
public class ControllerComputer extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ComputerPageHandlerStrategyService computerPaginationService;
	
	@Autowired
	private ComputerService computerService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerComputer.class);
	private final String ERROR404 = "/WEB-INF/views/404.html";

	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,config.getServletContext());
		super.init(config);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean isValid = true;
		if (req.getParameter("selection") != null) {
			isValid = deleteSelection(req);
			if (!isValid) {
				this.getServletContext().getRequestDispatcher(ERROR404).forward(req, resp);
			}
		}
		if (isValid) {
			int numberPage = computerPaginationService.getPageIndex();
			updateOnId(numberPage);
			handleRequest(req, resp);
			computerPaginationService.setPageChanged();
		}
		
		LOGGER.info("requete envoyee post dashboard");
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean isValid = true;
		if (req.getParameter("page") != null) {
			isValid = updatePage(req);
			if (!isValid) {
				this.getServletContext().getRequestDispatcher(ERROR404).forward(req, resp);
			}
		}
		if (req.getParameter("search") != null) {
			this.computerPaginationService.setSearchedEntry(req.getParameter("search"));
			this.computerPaginationService.changeState(State.SEARCH);
			if (req.getParameter("page") == null) {
				this.computerPaginationService.setLocalPageIndex(0);
			}
		}
		if (req.getParameter("search") == null && "1".equals(req.getParameter("page"))) {
			this.computerPaginationService.setSearchedEntry("");
			this.computerPaginationService.changeState(State.NORMAL);
		}
		if (isValid) {
			handleRequest(req, resp);
		}
		LOGGER.info("requete envoyee get dashboard");
	}

	private boolean updatePage(HttpServletRequest req) {
		try {
			int index = Integer.parseInt(req.getParameter("page")) - 1;
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

	private boolean deleteSelection(HttpServletRequest req) {
		List<String> idSelected = Arrays.asList(req.getParameter("selection").split(","));
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

	private void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ArrayList<Computer> computerList = computerPaginationService.getComputerPageList();
		req.setAttribute("pages", computerPaginationService.getButtonArray());
		req.setAttribute("testLeft", !computerPaginationService.testLeft());
		req.setAttribute("testRight", !computerPaginationService.testRight());
		req.setAttribute("numberComputer", computerPaginationService.getLocalNumberComputer());
		req.setAttribute("numberPage", computerPaginationService.getLocalNumberPage());
		req.setAttribute("computerList", ComputerMapper.computerListToDTOList(computerList));
		req.setAttribute("testNumber", computerPaginationService.getLocalNumberComputer() > 1);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
	}
}