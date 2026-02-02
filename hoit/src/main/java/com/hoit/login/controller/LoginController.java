package com.hoit.login.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoit.login.service.LoginService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/hoit")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@GetMapping(value = "/login.do")
	public String login(HttpServletRequest request) {
		return "login/login";
	}
	
	@GetMapping(value = "/createUser.do")
	public String createUser() {
		return "login/createUser";
	}
	
	@PostMapping(value = "/join_submit.do")
	@ResponseBody
	public void login_submit(@RequestBody Map<String, Object> param, HttpServletRequest request) throws Exception {
		param.put("USER_IP", com.hoit.util.GetClientIp.getClientIpAddr(request));
		loginService.userJoin(param);
	}
	
	
//	@PostMapping(value = "/hoit/login_check.do")
//	@ResponseBody
//	public Map<String, Object> login_check(@RequestBody Map<String, Object> param) throws Exception {
//		return loginService.getUserById(param);
//	}
}
