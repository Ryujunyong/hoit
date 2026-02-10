package com.hoit.accountbook.service;

import java.util.List;
import java.util.Map;

import com.hoit.common.CursorRequest;
import com.hoit.common.CursorResponse;

public interface AccountBookService {
	
	List accountBookList(Map<String, Object> map);
	CursorResponse getScrollList(Map<String, Object> map);
	int accountBookCnt();
	void writeAccountBook(Map<String, Object> map);
	void edieAccountBook(Map<String, Object> map);
	void deleteAccountBook(Map<String, Object> map);
	Map<String, Object> getCurrentMoney();
	Map<String, Object> getTotalAssets(Map<String, Object> map);
	void saveAsset(Map<String, Object> map);
	Map<String, Object> getMonthlyAmount(Map<String, Object> map);
	List<Map<String, Object>> getCategoryMonthlyAmount(Map<String, Object> map);
}
