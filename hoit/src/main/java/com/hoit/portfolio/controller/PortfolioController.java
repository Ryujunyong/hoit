package com.hoit.portfolio.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PortfolioController {
	
	
	@GetMapping(value = "/hoit/portfolio.do")
	public String portfolioMain(HttpServletRequest request) {
		return "portfolio/main";
	}
}
