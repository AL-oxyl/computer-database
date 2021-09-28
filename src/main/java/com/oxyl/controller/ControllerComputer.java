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
		
		ArrayList<Computer> computerList = computerPaginationService.getComputerPageList();
		req = loadInfo(req,computerList);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
	}
	
	private static HttpServletRequest loadInfo(HttpServletRequest req, ArrayList<Computer> computerList) {
		req.setAttribute("numberComputer", ComputerPageHandlerStrategyService.getNumberComputer());
		req.setAttribute("numberPage", ComputerPageHandlerStrategyService.getNumberPage());
		req.setAttribute("computerList", ComputerMapper.computerListToDTOList(computerList));
		return req;
	}
}