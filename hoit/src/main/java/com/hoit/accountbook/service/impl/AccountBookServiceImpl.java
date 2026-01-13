package com.hoit.accountbook.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoit.accountbook.mapper.AccountBookMapper;
import com.hoit.accountbook.service.AccountBookService;
import com.hoit.util.UniqueKey;

@Service
@Transactional
public class AccountBookServiceImpl implements AccountBookService {
	
	@Autowired
	private AccountBookMapper accountBookMapper;
	
	@Override
	public List accountBookList() {
		return accountBookMapper.accountBookList();
	}

	@Override
	public int accountBookCnt() {
		return accountBookMapper.accountBookCnt();
	}

	@Override
	public void writeAccountBook(Map<String, Object> map) {
		Map<String, Object> currentBalanceMap = accountBookMapper.getCurrentMoney();
		long currentBalance = Long.valueOf(String.valueOf(currentBalanceMap.get("CURRENT_BALANCE")));
		long inputMoney = Long.valueOf(String.valueOf(map.get("USE_AMOUNT")));
		long finalMoney = currentBalance - inputMoney;
		map.put("ACCOUNT_ID", UniqueKey.getKeyByDateFormat());
		map.put("ASSET_ID", UniqueKey.getKeyByDateFormat());
		map.put("CHARGE_AMOUNT", 0);
		map.put("CURRENT_BALANCE", finalMoney);
		accountBookMapper.writeAccountBook(map);
		accountBookMapper.chargeCash(map);
	}

	@Override
	public void edieAccountBook(Map<String, Object> map) {
		accountBookMapper.edieAccountBook(map);
	}
	
	@Override
	public void edieTotalMoney(Map<String, Object> map) {
		accountBookMapper.edieTotalMoney(map);
	}

	@Override
	public void deleteAccountBook(Map<String, Object> map) {
		accountBookMapper.deleteAccountBook(map);
	}
	
	@Override
	public Map<String, Object> getCurrentMoney() {
		return accountBookMapper.getCurrentMoney();
	}
	
	@Override
	public Map<String, Object> getTotalAssets(Map<String, Object> map) {
		return accountBookMapper.getTotalAssets(map);
	}

	@Override
	public void chargeCash(Map<String, Object> map) {
		map.put("ASSET_ID", UniqueKey.getKeyByDateFormat());
		Map<String, Object> currentBalanceMap = accountBookMapper.getCurrentMoney();
		long currentBalance = Long.valueOf(String.valueOf(currentBalanceMap.get("CURRENT_BALANCE")));
		long chargeAmount = Long.valueOf(String.valueOf(map.get("CHARGE_AMOUNT")));
		long currentMoney = currentBalance + chargeAmount;
		if(currentBalance == 0) {
			map.put("CURRENT_BALANCE", chargeAmount);
		} else {
			map.put("CURRENT_BALANCE", currentMoney);
		}
		accountBookMapper.chargeCash(map);
	}
}
