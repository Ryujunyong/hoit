package com.hoit.category.service;

import java.util.List;
import java.util.Map;

public interface CategoryService {

	List<?> selectCategoryList(Map<String, Object> map);
	int selectCategoryTotCnt();
	void insertCategory(Map<String, Object> map);
	void updateCategory(Map<String, Object> map);
}
