package com.oxyl.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.oxyl.mapper.ComputerMapper;
import com.oxyl.model.Computer;
import com.oxyl.service.ComputerPageHandlerStrategyService;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet("/dashboard")
public class ControllerComputer extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private ComputerPageHandlerStrategyService computerPaginationService;
	//private static final Logger LOGGER = LoggerFactory.getLogger(ControllerComputer.class);

	public ControllerComputer() {
		super();
		this.computerPaginationService = new ComputerPageHandlerStrategyService();
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
	}
	
	protected void doGet(HttpServletRequest req ,HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameter("page") != null) {
			updatePage(req);
		}
		ArrayList<Computer> computerList = computerPaginationService.getComputerPageList();
		req.setAttribute("pages", computerPaginationService.getButtonArray());
		req.setAttribute("testLeft", !computerPaginationService.testLeft());
		req.setAttribute("testRight", !computerPaginationService.testRight());
		req.setAttribute("numberComputer", ComputerPageHandlerStrategyService.getNumberComputer());
		req.setAttribute("numberPage", ComputerPageHandlerStrategyService.getNumberPage());
		req.setAttribute("computerList", ComputerMapper.computerListToDTOList(computerList));
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
	}
	
	private void updatePage(HttpServletRequest req ) {
		try {
			int index = Integer.parseInt(req.getParameter("page")) - 1;
			int lastPage = computerPaginationService.getPageIndex();
			computerPaginationService.setPageIndex(index);
			computerPaginationService.updateButtonArray(lastPage);
		} catch(NumberFormatException e){
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
}