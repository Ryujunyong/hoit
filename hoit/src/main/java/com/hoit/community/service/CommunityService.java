package com.hoit.community.service;

import java.util.List;
import java.util.Map;

public interface CommunityService {

	List selectCommunityList() throws Exception;
	Map<String, Object> getSelectCommunity(Map<String, Object> map) throws Exception;
	int selectCommunityTotCnt() throws Exception;
	void insertCommunity(Map<String, Object> map) throws Exception;
	void updateCommunity(Map<String, Object> map) throws Exception;
	void communityViewCnt(Map<String, Object> map) throws Exception;
	void deleteCommunity(Map<String, Object> map) throws Exception;
}
