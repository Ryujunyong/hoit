package com.hoit.cashback.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoit.cashback.mapper.CashBackMapper;
import com.hoit.cashback.service.CashBackService;
@Service
public class CashBackServiceImpl implements CashBackService {
	
	@Autowired
	private CashBackMapper cashBackMapper;
	
	@Override
	public void chargeCashBack(Map<String, Object> map) {
		cashBackMapper.chargeCashBack(map);
	}

}
