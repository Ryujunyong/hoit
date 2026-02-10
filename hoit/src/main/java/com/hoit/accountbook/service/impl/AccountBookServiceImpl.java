package com.hoit.accountbook.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hoit.accountbook.mapper.AccountBookMapper;
import com.hoit.accountbook.service.AccountBookService;
import com.hoit.common.CursorRequest;
import com.hoit.common.CursorResponse;
import com.hoit.util.UniqueKey;

@Service
@Transactional
public class AccountBookServiceImpl implements AccountBookService {
	
	@Autowired
	private AccountBookMapper accountBookMapper;
	
	@Override
	public List accountBookList(Map<String, Object> map) {
		map.put("recordCountPerPage", 10);
		map.put("firstIndex", 0);
		return accountBookMapper.accountBookList(map);
	}
	
	@Override
	public CursorResponse getScrollList(Map<String, Object> map) {
		int size = 0;
		if(map.containsKey("size")) {
			size = Integer.parseInt(String.valueOf(map.get("size")));
		}
		map.put("size", size + 1); // 다음 페이지 존재 여부 확인을 위해 1개 더 조회
		
		List<Map<String, Object>> list = accountBookMapper.selectScrollList(map);
		boolean hasNext = false;
		Map<String, Object> nextCursor = new HashMap<>();
		
		if (list.size() > size) {
			hasNext = true;
			list.remove(size);
		}
		if (!list.isEmpty()) {
			nextCursor.put("lastAccountDate", String.valueOf(list.get(list.size() - 1).get("ACCOUNT_DATE")));
			nextCursor.put("lastAccountId", String.valueOf(list.get(list.size() - 1).get("ACCOUNT_ID")));
		}
		
		return CursorResponse.<Map<String, Object>>builder()
				.data(list)
				.nextCursorId(nextCursor)
				.hasNext(hasNext)
				.build();
	}

	@Override
	public int accountBookCnt() {
		return accountBookMapper.accountBookCnt();
	}

	@Override
	public void writeAccountBook(Map<String, Object> map) {
		
		Map<String, Object> currentBalanceMap = accountBookMapper.getCurrentMoney();
		long currentBalance = Long.valueOf(String.valueOf(currentBalanceMap.get("CURRENT_BALANCE")));
		long realAmount = Long.valueOf(String.valueOf(map.get("AMOUNT")));
		if(map.get("ACCOUNT_TYPE").equals("O")) {
			realAmount = realAmount * - 1;
		}
		long finalMoney = currentBalance + realAmount;
		map.put("ACCOUNT_ID", UniqueKey.getKeyByDateFormat());
		map.put("AMOUNT", realAmount);
		map.put("ASSET_ID", currentBalanceMap.get("ASSET_ID"));
		map.put("CURRENT_BALANCE", finalMoney);
		accountBookMapper.writeAccountBook(map);
		accountBookMapper.renewAsset(map);
	}

	@Override
	public void edieAccountBook(Map<String, Object> map) {
		accountBookMapper.edieAccountBook(map);
	}
	
	@Override
	public void deleteAccountBook(Map<String, Object> map) {
		accountBookMapper.deleteAccountBook(map);
		accountBookMapper.deleteAsset(map);
	}
	
	@Override
	public Map<String, Object> getCurrentMoney() {
		Map<String, Object> currentMoneyMap = accountBookMapper.getCurrentMoney();
		if(currentMoneyMap == null) {
			currentMoneyMap = new HashMap<>();
			currentMoneyMap.put("CURRENT_BALANCE", 0);
			currentMoneyMap.put("CURRENT_STATUS", 'N');
		}
		currentMoneyMap.put("CURRENT_STATUS", 'Y');
		return currentMoneyMap;
	}
	
	@Override
	public Map<String, Object> getTotalAssets(Map<String, Object> map) {
		return accountBookMapper.getTotalAssets(map);
	}

	@Override
	public void saveAsset(Map<String, Object> map) {
		map.put("ASSET_ID", UniqueKey.getKeyByDateFormat());
		map.put("ACCOUNT_ID", UniqueKey.getKeyByDateFormat());
		map.put("ACCOUNT_TYPE", "S");
		map.put("AMOUNT", map.get("CURRENT_BALANCE"));
		map.put("DESCRIPTION", "개설");
		accountBookMapper.saveAsset(map);
		accountBookMapper.writeAccountBook(map);
	}
	
	@Override
	public Map<String, Object> getMonthlyAmount(Map<String, Object> map) {
		return accountBookMapper.getMonthlyAmount(map);
	}
	
	@Override
	public List<Map<String, Object>> getCategoryMonthlyAmount(Map<String, Object> map) {
		return accountBookMapper.getCategoryMonthlyAmount(map);
	}

}
