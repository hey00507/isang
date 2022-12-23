package com.isang.api.entity.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Setter
public abstract class PagingSearch {


    private static final int MAX_SIZE = 2000;
    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer size = 10;

    private boolean paging = true;

    public long getOffset(){
        return (long) (max(1,page) -1) * min(size, MAX_SIZE);
    }
}
