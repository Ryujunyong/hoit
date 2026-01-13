package com.hoit.community.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommunityMapper {

	// 커뮤니티 조회
	List selectCommunityList() throws Exception;
	// 커뮤니티 상세조회
	Map<String, Object> getSelectCommunity(Map<String, Object> map) throws Exception;
	// 커뮤니티 총 건수 조회
	int selectCommunityTotCnt() throws Exception;
	// 커뮤니티 등록
	void insertCommunity(Map<String, Object> map) throws Exception;
	// 커뮤니티 수정
	void updateCommunity(Map<String, Object> map) throws Exception;
	// 조회수 증가
	void communityViewCnt(Map<String, Object> map) throws Exception;
	// 커뮤니티 삭제
	void deleteCommunity(Map<String, Object> map) throws Exception;
}
