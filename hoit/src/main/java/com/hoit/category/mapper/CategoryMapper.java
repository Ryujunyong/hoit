package com.hoit.category.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

	// 카테고리 조회
	List<?> selectCategoryList(Map<String, Object> map);
	// 카테고리 갯 수 조회
	int selectCategoryTotCnt();
	// 카테고리 등록
	void insertCategory(Map<String, Object> map);
	// 카테고리 수정
	void updateCategory(Map<String, Object> map);
}