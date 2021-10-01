package com.oxyl.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.mapper.ComputerMapper;
import com.oxyl.model.Computer;
import com.oxyl.service.ComputerPageHandlerStrategyService;
import com.oxyl.service.ComputerService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet("/dashboard")
public class ControllerComputer extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private ComputerPageHandlerStrategyService computerPaginationService;
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerComputer.class);

	public ControllerComputer() {
		super();
		this.computerPaginationService = new ComputerPageHandlerStrategyService();
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		boolean isValid = true;
		if(req.getParameter("selection") != null) {
			isValid = deleteSelection(req);
			if(!isValid) {
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/404.html").forward(req, resp);
			}
		}
		if(isValid) {
			handleRequest(req,resp);
			updateOnId(0);
			computerPaginationService.setPageChanged();
		}
		LOGGER.info("requete envoyee post dashboard");
	}
	
	protected void doGet(HttpServletRequest req ,HttpServletResponse resp) throws ServletException, IOException {
		boolean isValid = true;
		if (req.getParameter("page") != null) {
			isValid = updatePage(req);
			if(!isValid) {
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/404.html").forward(req, resp);
			}
		}
		if(isValid) {
			handleRequest(req,resp);
		}
		LOGGER.info("requete envoyee get dashboard");
	}
	
	private boolean updatePage(HttpServletRequest req ) {
		try {
			int index = Integer.parseInt(req.getParameter("page")) - 1;
			updateOnId(index);
			return true;
		} catch(NumberFormatException e){
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
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
			for(String id : idSelected) {
				ComputerService.deleteComputer(Integer.parseInt(id));
			}
			return true;
		} catch (NumberFormatException e){
			LOGGER.error("Array contains string not parsable");
		}
		return false;
	}
	
	private void handleRequest(HttpServletRequest req ,HttpServletResponse resp)throws ServletException, IOException {
		ArrayList<Computer> computerList = computerPaginationService.getComputerPageList();
		req.setAttribute("pages", computerPaginationService.getButtonArray());
		req.setAttribute("testLeft", !computerPaginationService.testLeft());
		req.setAttribute("testRight", !computerPaginationService.testRight());
		req.setAttribute("numberComputer", ComputerPageHandlerStrategyService.getNumberComputer());
		req.setAttribute("numberPage", ComputerPageHandlerStrategyService.getNumberPage());
		req.setAttribute("computerList", ComputerMapper.computerListToDTOList(computerList));
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
	}
}