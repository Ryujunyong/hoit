package com.hoit.portfolio.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.hoit.login.service.LoginService;
import com.hoit.util.GetClientIp;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PortfolioController {
	
	@Autowired
	private LoginService loginService;
	
	@GetMapping(value = "/hoit/portfolio.do")
	public String portfolioMain(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		map.put("IP", GetClientIp.getClientIpAddr(request));
		loginService.saveClientIp(map);
		return "portfolio/main";
	}
}
