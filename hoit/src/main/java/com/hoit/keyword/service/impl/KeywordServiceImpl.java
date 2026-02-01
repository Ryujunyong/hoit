package com.hoit.keyword.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoit.keyword.mapper.KeywordMapper;
import com.hoit.keyword.service.KeywordService;
import com.hoit.util.UniqueKey;
@Service
public class KeywordServiceImpl implements KeywordService {

	@Autowired
	private KeywordMapper keywordMapper;

	@Override
	public List<?> selectKeywordList() {
		return keywordMapper.selectKeywordList();
	}

	@Override
	public int selectKeywordTotCnt() {
		return keywordMapper.selectKeywordTotCnt();
	}

	@Override
	public void writeKeyword(Map<String, Object> map) {
		map.put("KEYWORD_ID", UniqueKey.getKeyByDateFormat());
		keywordMapper.writeKeyword(map);
	}

	@Override
	public void updateKeyword(Map<String, Object> map) {
		keywordMapper.updateKeyword(map);
	}

	@Override
	public void deleteKeyword(Map<String, Object> map) {
		keywordMapper.deleteKeyword(map);
	}
}
