package com.hoit.accountbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountBookMapper {
	// 가계부 목록 조회
	List accountBookList();
	// 가계부 갯수
	int accountBookCnt();
	// 가계부 작성
	void writeAccountBook(Map<String, Object> map);
	// 가계부 수정
	void edieAccountBook(Map<String, Object> map);
	// 금액 갱신
	void renewAsset(Map<String, Object> map);
	// 가계부 삭제
	void deleteAccountBook(Map<String, Object> map);
	// 금액 제거
	void deleteAsset(Map<String, Object> map);
	// 현재 금액 조회
	Map<String, Object> getCurrentMoney();
	// 금액 조회
	Map<String, Object> getTotalAssets(Map<String, Object> map);
	// 자산 등록
	void saveAsset(Map<String, Object> map);
	
}
