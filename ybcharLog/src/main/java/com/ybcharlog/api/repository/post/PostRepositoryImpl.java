package com.ybcharlog.api.repository.post;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ybcharlog.api.Common.repository.BasicRepoSupport;
import com.ybcharlog.api.RequestDto.post.PostEditDto;
import com.ybcharlog.api.RequestDto.post.PostSearchDto;
import com.ybcharlog.api.ResponseDto.comment.CommentResponse;
import com.ybcharlog.api.ResponseDto.post.PostResponse;
import com.ybcharlog.api.domain.post.Post;
import com.ybcharlog.api.domain.post.QPost;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

import static com.ybcharlog.api.RequestDto.post.PostSearchDto.*;
import static com.ybcharlog.api.domain.comment.QComment.*;
import static com.ybcharlog.api.domain.post.QPost.*;


@Repository
public class PostRepositoryImpl extends BasicRepoSupport implements PostRepositoryCustom {

    private static final QPost post = QPost.post;

    protected PostRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(jpaQueryFactory);
    }

    @Override
    public Post getPostOne(Long postId) {
        Post post = jpaQueryFactory
                .selectFrom(QPost.post)
                .innerJoin(QPost.post.comments).fetchJoin()
                .where(QPost.post.id.eq(postId))
                .fetchOne();
        if (post == null)
            throw new IllegalArgumentException("존재하지 않는 글입니다.");
        return post;
    }

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

    @Override
    public Page<Post> getPostListByPage(GetPostPageReq req, Pageable pageable) {
        JPAQuery<Post> query = jpaQueryFactory.selectFrom(post).join(post.comments).fetchJoin();
//		this.setWhereQueryForFindAllNotice(query, req);
        super.setPageQuery(query, pageable, post);
        List<Post> result = query.fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(post.id.count())
                .from(post);
        Long count = countQuery.fetchOne();
        count = count == null ? 0 : count;

        return new PageImpl<>(result, super.getValidPageable(pageable), count);

    }
}
