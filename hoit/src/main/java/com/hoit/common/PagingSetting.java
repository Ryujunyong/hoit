package com.hoit.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class PagingSetting {

	public Map<String, Object> CommonPaging(PageVO page, int tot) {
		Map<String, Object> map = new HashMap<>();
		Pagination pn = new Pagination();
		pn.setCurrentPageNo(page.getPageIndex());
		pn.setRecordCountPerPage(page.getPageUnit());
		pn.setPageSize(page.getPageSize());
		page.setFirstIndex(pn.getFirstRecordIndex());
		page.setRecordCountPerPage(pn.getRecordCountPerPage());
		pn.setTotalRecordCount(tot);
		page.setEndData(pn.getLastPageNoOnPageList());
		page.setStartData(pn.getFirstPageNoOnPageList());
		page.setPrev(pn.isPrev());
		page.setNext(pn.isNext());
		page.setEndPage(pn.getEndPage());
		
		map.put("totalPageCnt", (int) Math.ceil(tot / (double) page.getPageUnit()));
		map.put("pageVO", page);
		map.put("recordCountPerPage", pn.getRecordCountPerPage());
		map.put("firstIndex", page.getFirstIndex());
		
		return map;
	}
}
