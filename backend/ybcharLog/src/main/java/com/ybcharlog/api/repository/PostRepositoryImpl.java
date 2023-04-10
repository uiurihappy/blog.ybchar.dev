package com.ybcharlog.api.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ybcharlog.api.RequestDto.PostEditDto;
import com.ybcharlog.api.RequestDto.PostSearchDto;
import com.ybcharlog.api.domain.Post;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ybcharlog.api.domain.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(PostSearchDto postSearchDto) {
        return jpaQueryFactory.selectFrom(post)
                .limit(postSearchDto.getSize())
                .offset(postSearchDto.getOffset(postSearchDto.getPage(), postSearchDto.getSize()))
                .orderBy(post.id.desc())
                .fetch();
    }

    @Override
    public void editPost(Long postId, PostEditDto postEditDto) {
        jpaQueryFactory.update(post)
                .set(post.title, postEditDto.getTitle())
                .set(post.content, postEditDto.getContent())
                .where(post.id.eq(postId))
                .execute();
    }
}
