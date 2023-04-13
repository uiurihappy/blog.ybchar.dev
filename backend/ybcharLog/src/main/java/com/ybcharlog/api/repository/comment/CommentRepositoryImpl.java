package com.ybcharlog.api.repository.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ybcharlog.api.RequestDto.comment.CommentEditDto;
import com.ybcharlog.api.RequestDto.comment.CommentSearchDto;
import com.ybcharlog.api.domain.comment.Comment;
import com.ybcharlog.api.domain.comment.QComment;
import com.ybcharlog.api.domain.post.QPost;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ybcharlog.api.domain.comment.QComment.*;


@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> getList(Long postId, CommentSearchDto commentSearchDto) {
        return jpaQueryFactory.selectFrom(comment)
                .leftJoin(comment.post, QPost.post)
                .limit(commentSearchDto.getSize())
                .offset(commentSearchDto.getOffset(commentSearchDto.getPage(), commentSearchDto.getSize()))
                .orderBy(comment.id.desc())
                .fetch();
    }
    @Override
    public void editComment(Long commentId, CommentEditDto commentEditDto) {
        jpaQueryFactory.update(comment)
                .set(comment.commentContent, commentEditDto.getCommentContent())
                .where(comment.id.eq(commentId))
                .execute();
    }
}
