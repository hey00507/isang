package com.isang.api.repository;

import com.isang.api.domain.Post;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(int page);
}
