package com.hoit.category.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoit.category.mapper.CategoryMapper;
import com.hoit.category.service.CategoryService;
import com.hoit.util.UniqueKey;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryMapper cateGoryMapper;
	@Override
	public List<?> selectCategoryList(Map<String, Object> map) {
		return cateGoryMapper.selectCategoryList(map);
	}

	@Override
	public int selectCategoryTotCnt() {
		return cateGoryMapper.selectCategoryTotCnt();
	}

	@Override
	public void insertCategory(Map<String, Object> map) {
		map.put("CATEGORY_ID", UniqueKey.getKeyByDateFormat());
		cateGoryMapper.insertCategory(map);
	}

	@Override
	public void updateCategory(Map<String, Object> map) {
		cateGoryMapper.updateCategory(map);
	}

}
