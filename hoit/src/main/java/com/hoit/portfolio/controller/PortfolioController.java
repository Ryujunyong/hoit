package com.hoit.portfolio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PortfolioController {

	@GetMapping(value = "/hoit/portfolio.do")
	public String portfolioMain() {
		return "portfolio/main";
	}
}
