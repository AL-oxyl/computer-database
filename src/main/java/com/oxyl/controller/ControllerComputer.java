package com.oxyl.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

@WebServlet("/dashboard")
public class ControllerComputer extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	public ControllerComputer() {
		super();
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		this.getServletContext().getRequestDispatcher("/static/views/dashboard.html").forward(req, resp);
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(getServletContext().getResource(getServletInfo()));
		this.getServletContext().getRequestDispatcher("/static/views/dashboard.html").forward(req, resp);
	}
}
