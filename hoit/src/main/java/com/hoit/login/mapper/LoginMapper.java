package com.hoit.login.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {

	List<?> userList() throws Exception;
	
	Map<String, Object> getUserById(Map<String, Object> map);
	
	void userJoin(Map<String, Object> map) throws Exception;
}
