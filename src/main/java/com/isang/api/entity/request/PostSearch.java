package com.isang.api.entity.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Getter
@Setter
@Builder
public class PostSearch extends PagingSearch {

    private String title;
    private String content;

}
