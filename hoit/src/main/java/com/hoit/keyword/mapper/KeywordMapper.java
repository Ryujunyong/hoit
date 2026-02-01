package com.hoit.keyword.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KeywordMapper {
	// 키워드 조회
	List<?> selectKeywordList();
	// 키워드 갯 수 조회
	int selectKeywordTotCnt();
	// 키워드 등록
	void writeKeyword(Map<String, Object> map);
	// 키워드 수정
	void updateKeyword(Map<String, Object> map);
	// 키워드 삭제
	void deleteKeyword(Map<String, Object> map);
}
