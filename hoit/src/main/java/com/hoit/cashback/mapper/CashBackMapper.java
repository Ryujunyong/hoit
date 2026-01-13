package com.hoit.cashback.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CashBackMapper {

	void chargeCashBack(Map<String, Object> map);
}
