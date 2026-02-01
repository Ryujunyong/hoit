package com.hoit.login.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hoit.login.mapper.LoginMapper;
import com.hoit.login.service.LoginService;
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginMapper loginMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public List<?> userList() throws Exception {
		return loginMapper.userList();
	}

	@Override
	public Map<String, Object> getUserById(Map<String, Object> map) {
		return loginMapper.getUserById(map);
	}

	@Override
	public void userJoin(Map<String, Object> map) throws Exception {
		map.put("USER_PW", passwordEncoder.encode(String.valueOf(map.get("USER_PW"))));
		loginMapper.userJoin(map);
	}
	
}
