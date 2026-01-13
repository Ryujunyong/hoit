package com.hoit.community.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoit.community.mapper.CommunityMapper;
import com.hoit.community.service.CommunityService;
import com.hoit.util.UniqueKey;

@Service
public class CommunityServiceImpl implements CommunityService {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CommunityMapper communityDAO;
	@Override
	public List selectCommunityList() throws Exception {
		return communityDAO.selectCommunityList();
	}
	
	@Override
	public Map<String, Object> getSelectCommunity(Map<String, Object> map) throws Exception {
		return communityDAO.getSelectCommunity(map);
	}
	
	@Override
	public int selectCommunityTotCnt() throws Exception {
		return communityDAO.selectCommunityTotCnt();
	}
	
	@Override
	public void insertCommunity(Map<String, Object> map) throws Exception { 
		map.put("commu_cd", "COMMU"+UniqueKey.getKeyByDateFormat());
		communityDAO.insertCommunity(map);
	}
	
	@Override
	public void updateCommunity(Map<String, Object> map) throws Exception {
		communityDAO.updateCommunity(map);
	}

	@Override
	public void deleteCommunity(Map<String, Object> map) throws Exception {
		communityDAO.deleteCommunity(map);
	}
	@Override
	public void communityViewCnt(Map<String, Object> map) throws Exception {
		communityDAO.communityViewCnt(map);
	}
}
