package com.ybcharlog.api.ResponseDto.post;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.ybcharlog.api.ResponseDto.post.QPostResponse is a Querydsl Projection type for PostResponse
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QPostResponse extends ConstructorExpression<PostResponse> {

    private static final long serialVersionUID = 1226291989L;

    public QPostResponse(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> title, com.querydsl.core.types.Expression<String> content, com.querydsl.core.types.Expression<Integer> viewCount, com.querydsl.core.types.Expression<Integer> likeCount, com.querydsl.core.types.Expression<? extends java.util.List<com.ybcharlog.api.domain.comment.Comment>> comments) {
        super(PostResponse.class, new Class<?>[]{long.class, String.class, String.class, int.class, int.class, java.util.List.class}, id, title, content, viewCount, likeCount, comments);
    }

}

