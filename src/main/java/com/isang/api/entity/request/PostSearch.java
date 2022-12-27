package com.isang.api.entity.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostSearch extends PagingSearch {

    private String title;
    private String content;

}
