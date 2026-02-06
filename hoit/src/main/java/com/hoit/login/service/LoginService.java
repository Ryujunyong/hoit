package com.hoit.login.service;

import java.util.List;
import java.util.Map;

public interface LoginService {

	List<?> userList() throws Exception;
	
	Map<String, Object> getUserById(Map<String, Object> map);
	
	void userJoin(Map<String, Object> map) throws Exception;
	
}
