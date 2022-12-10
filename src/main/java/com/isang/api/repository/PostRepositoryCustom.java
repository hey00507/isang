package com.isang.api.repository;

import com.isang.api.domain.Post;
import com.isang.api.entity.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
