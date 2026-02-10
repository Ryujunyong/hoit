package com.hoit.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CursorRequest {
    private Long commonCd;
    private int size = 10;
	public Long getCommonCd() {
		return commonCd;
	}
	public void setCommonCd(Long commonCd) {
		this.commonCd = commonCd;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
    
}
