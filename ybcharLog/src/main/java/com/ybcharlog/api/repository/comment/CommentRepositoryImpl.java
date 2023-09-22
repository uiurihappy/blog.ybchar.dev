package com.ybcharlog.api.repository.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ybcharlog.api.common.repository.BasicRepoSupport;
import com.ybcharlog.api.RequestDto.comment.CommentEditDto;
import com.ybcharlog.api.RequestDto.comment.CommentSearchDto;
import com.ybcharlog.api.domain.comment.Comment;
import com.ybcharlog.api.domain.post.QPost;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import java.util.List;

import static com.ybcharlog.api.domain.comment.QComment.comment;


@Repository
public class CommentRepositoryImpl extends BasicRepoSupport implements CommentRepositoryCustom{
    protected CommentRepositoryImpl(JPAQueryFactory jpaQueryFactory, EntityManager em) {
        super(jpaQueryFactory, em);
    }

    @Override
    public List<Comment> getList(Long postId, CommentSearchDto commentSearchDto) {
        return jpaQueryFactory.selectFrom(comment)
                .leftJoin(comment.post, QPost.post)
                .where(comment.isDeleted.eq(0))
                .where(comment.display.eq(1))
                .limit(commentSearchDto.getSize())
                .offset(commentSearchDto.getOffset(commentSearchDto.getPage(), commentSearchDto.getSize()))
                .orderBy(comment.id.desc())
                .fetch();
    }
    @Override
    public void editComment(Long commentId, CommentEditDto commentEditDto) {
        jpaQueryFactory.update(comment)
                .set(comment.commentContent, commentEditDto.getCommentContent())
                .where(comment.isDeleted.eq(0))
                .where(comment.display.eq(1))
                .where(comment.id.eq(commentId))
                .execute();
        em.clear();
    }
}
