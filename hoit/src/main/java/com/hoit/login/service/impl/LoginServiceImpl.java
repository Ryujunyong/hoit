package com.hoit.login.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoit.login.mapper.LoginMapper;
import com.hoit.login.service.LoginService;
@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginMapper loginMapper;
	
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
		loginMapper.userJoin(map);
	}
	
	@Override
	public void saveClientIp(Map<String, Object> map) {
		loginMapper.saveClientIp(map);
	}
	
}
