package com.hoit.common;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CursorResponse<T> {
    private List<T> data;
    private Object nextCursorId;
    private boolean hasNext;
    
}