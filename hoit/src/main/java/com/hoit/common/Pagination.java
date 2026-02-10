package com.hoit.common;

public class Pagination {

	private int currentPageNo;
	private int recordCountPerPage;
	private int pageSize;
	private int totalRecordCount;
	private int endPage;
	
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}
	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	public int getEndPage() {
		endPage = (int) (Math.ceil((getTotalRecordCount() * 1.0) / getRecordCountPerPage()));
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	private int firstPageNoOnPageList;
	private int lastPageNoOnPageList;
	private int firstRecordIndex;
	
	private boolean prev;
	private boolean next;

	public int getFirstPageNoOnPageList() {
		lastPageNoOnPageList = (int) (Math.ceil(currentPageNo / 10.0)) * 10;
		firstPageNoOnPageList = lastPageNoOnPageList - 9;
		return firstPageNoOnPageList;
	}
	public void setFirstPageNoOnPageList(int firstPageNoOnPageList) {
		this.firstPageNoOnPageList = firstPageNoOnPageList;
	}
	public int getLastPageNoOnPageList() {
		lastPageNoOnPageList = (int) (Math.ceil(getCurrentPageNo() / 10.0)) * 10;
		int endPage = (int) (Math.ceil((getTotalRecordCount() * 1.0) / getRecordCountPerPage()));
		if(endPage < lastPageNoOnPageList) {
			lastPageNoOnPageList = endPage;
		}
		return lastPageNoOnPageList;
	}
	public void setLastPageNoOnPageList(int lastPageNoOnPageList) {
		this.lastPageNoOnPageList = lastPageNoOnPageList;
	}
	public int getFirstRecordIndex() {
		firstRecordIndex = (getCurrentPageNo() - 1) * getRecordCountPerPage();
		System.out.println("\n\n\n"+getCurrentPageNo());
		System.out.println("\n\n\n"+getRecordCountPerPage());
		System.out.println("\n\n\n"+firstRecordIndex);
		return firstRecordIndex;
	}
	public void setFirstRecordIndex(int firstRecordIndex) {
		this.firstRecordIndex = firstRecordIndex;
	}
	public boolean isPrev() {
		prev = currentPageNo > 1;
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		int endPage = (int) (Math.ceil((getTotalRecordCount() * 1.0) / getRecordCountPerPage()));
		System.out.println("\n\n\n"+endPage);
		System.out.println(getTotalRecordCount());
		System.out.println(getRecordCountPerPage());
		System.out.println(currentPageNo);
		next = currentPageNo < endPage;
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	
	
	
}
