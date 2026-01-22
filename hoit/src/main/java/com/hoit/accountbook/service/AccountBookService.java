package com.hoit.accountbook.service;

import java.util.List;
import java.util.Map;

public interface AccountBookService {
	
	List accountBookList();
	int accountBookCnt();
	void writeAccountBook(Map<String, Object> map);
	void edieAccountBook(Map<String, Object> map);
	void deleteAccountBook(Map<String, Object> map);
	Map<String, Object> getCurrentMoney();
	Map<String, Object> getTotalAssets(Map<String, Object> map);
	void saveAsset(Map<String, Object> map);
}
