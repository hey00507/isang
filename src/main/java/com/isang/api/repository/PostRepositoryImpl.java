package com.isang.api.repository;

import com.isang.api.domain.Post;
import com.isang.api.domain.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Post> getList(int page) {
        return jpaQueryFactory.selectFrom(QPost.post)
                .limit(10)
                .offset((long)(page - 1) * 10)
                .orderBy(QPost.post.id.desc())
                .fetch();
    }
}
