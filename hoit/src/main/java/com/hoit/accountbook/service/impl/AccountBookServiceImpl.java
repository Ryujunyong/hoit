package com.hoit.accountbook.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hoit.accountbook.mapper.AccountBookMapper;
import com.hoit.accountbook.service.AccountBookService;
import com.hoit.common.CursorResponse;
import com.hoit.keyword.service.KeywordService;
import com.hoit.util.ExcelUpload;
import com.hoit.util.UniqueKey;

import jakarta.servlet.http.HttpServletRequest;

@Service
@Transactional
public class AccountBookServiceImpl implements AccountBookService {
	
	@Autowired
	private AccountBookMapper accountBookMapper;
	@Autowired
	private KeywordService keywordService;
	
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
		} else {
			currentMoneyMap.put("CURRENT_STATUS", 'Y');
		}
		return currentMoneyMap;
	}
	
	@Override
	public Map<String, Object> getTotalAssets(Map<String, Object> map) {
		return accountBookMapper.getTotalAssets(map);
	}

	@Override
	public void saveAsset(Map<String, Object> map) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		map.put("ASSET_ID", UniqueKey.getKeyByDateFormat());
		map.put("ACCOUNT_ID", UniqueKey.getKeyByDateFormat());
		map.put("ACCOUNT_TYPE", "S");
		map.put("AMOUNT", map.get("CURRENT_BALANCE"));
		map.put("ACCOUNT_DATE", sdf.format(date));
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
	
	@Override
	public int excelUpload(MultipartHttpServletRequest request, MultipartFile excelFile) throws Exception {
		// 1. 파일 업로드
		String filePath = setExcelUrl(excelFile, request);
		File file = new File(filePath);
		// 2. 엑셀 읽기
		String[] cellTitle = {"거래일시", "입금금액", "출금금액", "잔액", "적요내용"};
		ExcelUpload excelUpload = new ExcelUpload();
		DateTimeFormatter inFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
		DateTimeFormatter outFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<Map<String, Object>> list = excelUpload.upExcel(filePath, cellTitle);
		List<Map<String, Object>> keywordList = (List<Map<String, Object>>) keywordService.selectKeywordList();
		// 우선순위 높은 순으로 정렬
		keywordList.sort((k1, k2) -> {
			int p1 = Integer.parseInt(String.valueOf(k1.get("PRIORITY")));
			int p2 = Integer.parseInt(String.valueOf(k2.get("PRIORITY")));
			return Integer.compare(p2, p1);
		});
		// 자산 ID 가져오기
		Object getAssetId = accountBookMapper.getCurrentMoney().get("ASSET_ID");
		
		// 엑셀의 첫 번째 데이터 가져와 해당 잔액으로 자산 업데이트
		if (!list.isEmpty()) {
			Map<String, Object> firstRow = list.get(0);
			Long balance = Long.parseLong(String.valueOf(firstRow.get("잔액")).replaceAll("[^0-9]", ""));
			Map<String, Object> assetMap = new HashMap<>();
			assetMap.put("ASSET_ID", getAssetId);
			assetMap.put("CURRENT_BALANCE", balance);
			accountBookMapper.renewAsset(assetMap);
		}
		
		int cnt = 0;
		for(Map<String, Object> m : list) {
			Map<String, Object> map = new HashMap<>();
			String description = String.valueOf(m.get("적요내용"));
			
			// 메모리 상에서 키워드 매칭
			for(Map<String, Object> keyword : keywordList) {
				String keywordNm = String.valueOf(keyword.get("KEYWORD_NM"));
				if(description.contains(keywordNm)) {
					map.put("CATEGORY_ID", keyword.get("CATEGORY_ID"));
					break;
				}
			}
			
			long deposit = Long.parseLong(String.valueOf(m.get("입금금액")).replaceAll("[^0-9]", ""));
			long withdraw = Long.parseLong(String.valueOf(m.get("출금금액")).replaceAll("[^0-9]", "")) * - 1;
			if(deposit != 0) {
				map.put("ACCOUNT_TYPE", "I");
				map.put("AMOUNT", deposit);
			} else if (withdraw != 0) {
				map.put("ACCOUNT_TYPE", "O");
				map.put("AMOUNT", withdraw);
			}
			map.put("DESCRIPTION", description);
			map.put("ACCOUNT_ID", com.hoit.util.UniqueKey.getKeyByDateFormat());
			map.put("ACCOUNT_DATE", LocalDateTime.parse((String)m.get("거래일시"), inFormatter).format(outFormatter));
			map.put("ASSET_ID", getAssetId);
			if(map.get("CATEGORY_ID") == null) {
				cnt++;
			}
			accountBookMapper.writeAccountBook(map);
		}
		return cnt;
	}
		
	public String setExcelUrl( MultipartFile item, HttpServletRequest request ) throws Exception{
		String uploadFileName = item.getOriginalFilename();								//업로드된 파일명
    	String savePath = request.getSession().getServletContext().getRealPath("/");	//저장경로

	    String fileName = com.hoit.util.UniqueKey.getKeyByDateFormat() +"."+ uploadFileName.substring(uploadFileName.lastIndexOf(".")+1); //파일명 생성
    	String filePath = "excel" + fileName;		//DB 저장용 파일경로
    	String fileFullPath = savePath + filePath;					//저장전체경로

    	File uploadFile = new File(fileFullPath);
    	item.transferTo(uploadFile);

    	return fileFullPath;
	}
	
	@Override
	public void editCategory(Map<String, Object> map) {
		accountBookMapper.editCategory(map);
	}
}
