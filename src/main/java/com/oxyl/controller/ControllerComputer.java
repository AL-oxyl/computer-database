package com.oxyl.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oxyl.dto.ComputerDTO;
import com.oxyl.service.ComputerPageHandlerStrategyService;

import java.io.IOException;
import java.util.ArrayList;

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
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int numberComputer = computerPaginationService.getNumberComputer();
		ArrayList<ComputerDTO> computerList = computerPaginationService.getComputerPageList();
		req.setAttribute("numberComputer", numberComputer);
		req.setAttribute("computerList", computerList);
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req, resp);
	}
	
}