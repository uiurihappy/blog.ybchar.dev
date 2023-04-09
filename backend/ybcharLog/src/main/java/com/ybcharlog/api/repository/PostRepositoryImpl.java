package com.ybcharlog.api.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ybcharlog.api.domain.Post;
import com.ybcharlog.api.domain.QPost;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(int page) {
        return jpaQueryFactory.selectFrom(QPost.post)
                .fetch();
    }
}
