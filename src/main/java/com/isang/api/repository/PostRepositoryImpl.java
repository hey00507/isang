package com.isang.api.repository;

import com.isang.api.domain.Post;
import com.isang.api.domain.QPost;
import com.isang.api.entity.request.PostSearch;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class PostRepositoryImpl extends QuerydslRepositorySupport implements PostRepositoryCustom {

    public PostRepositoryImpl(Class<?> domainClass) {
        super(domainClass);
    }

    @Override
    public List<Post> getList(PostSearch postSearch) {

        QPost post = QPost.post;

        JPQLQuery<Post> query = from(post);

        query.where(
                titleLike(postSearch.getTitle())
                , contentLike(postSearch.getContent())
        );

        if(postSearch.isPaging()){
            query.offset(postSearch.getOffset()).limit(postSearch.getSize());
        }

        query.orderBy(post.id.desc());

        return query.fetch();
    }

    private BooleanExpression contentLike(String content) {
        if(content == null || content.isEmpty()){
            return null;
        }
        return QPost.post.content.like("%" + content + "%");
    }

    private BooleanExpression titleLike(String title) {
        if(title == null || title.isEmpty()){
            return null;
        }
        return QPost.post.title.like("%" + title + "%");
    }
}
