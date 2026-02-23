package com.hoit.keyword.service;

import java.util.List;
import java.util.Map;

public interface KeywordService {

	List<Map<String, Object>> selectKeywordList();
	Map<String, Object> getKeyword(Map<String, Object> map);
	int selectKeywordTotCnt();
	void writeKeyword(Map<String, Object> map);
	void updateKeyword(Map<String, Object> map);
	void deleteKeyword(Map<String, Object> map);
}
