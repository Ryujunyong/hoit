package com.hoit.login.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoit.login.service.LoginService;

@Controller("kr.or.hoit.login.web.LoginController")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@GetMapping(value = "/hoit/login.do")
	public String login() {
		return "login/login";
	}
	
	@PostMapping(value = "/hoit/login_submit.do")
	@ResponseBody
	public void login_submit(@RequestParam Map<String, Object> param) throws Exception {
		loginService.userJoin(param);
	}
	
	@PostMapping(value = "/hoit/login_check.do")
	@ResponseBody
	public Map<?, ?> login_check(@RequestParam Map<String, Object> param) throws Exception {
		System.out.println("test");
		return loginService.getUser(param);
	}
}
