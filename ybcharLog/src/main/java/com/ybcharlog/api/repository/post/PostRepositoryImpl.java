package com.ybcharlog.api.repository.post;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ybcharlog.api.Common.repository.BasicRepoSupport;
import com.ybcharlog.api.RequestDto.post.PostEditDto;
import com.ybcharlog.api.RequestDto.post.PostSearchDto;
import com.ybcharlog.api.ResponseDto.post.PostResponse;
import com.ybcharlog.api.ResponseDto.post.PostResponse.GetPostPageReq;
import com.ybcharlog.api.domain.post.Post;
import com.ybcharlog.api.domain.post.QPost;
import com.ybcharlog.api.exception.PostNotFound;
import com.ybcharlog.api.mapper.post.GetPostResDtoMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


@Repository
public class PostRepositoryImpl extends BasicRepoSupport implements PostRepositoryCustom {

    private static final QPost post = QPost.post;

    protected PostRepositoryImpl(JPAQueryFactory jpaQueryFactory, EntityManager em) {
        super(jpaQueryFactory, em);
    }

    @Override
    public PostResponse getPostOne(Long postId) {
        PostResponse post = GetPostResDtoMapper.INSTANCE.toDto(jpaQueryFactory
                .selectFrom(QPost.post)
                .leftJoin(QPost.post.comments).fetchJoin()
                .where(QPost.post.id.eq(postId))
                .where(QPost.post.isDeleted.eq(0))
                .where(QPost.post.display.eq(1))
                .fetchOne());

        if (post == null)
            throw new PostNotFound();
        else {
            jpaQueryFactory.update(QPost.post)
                    .set(QPost.post.viewCount, QPost.post.viewCount.add(1))
                    .where(QPost.post.id.eq(postId))
                    .execute();
            em.clear();
        }
        return post;
    }

    @Override
    public List<Post> getList(PostSearchDto postSearchDto) {
        return jpaQueryFactory.selectFrom(post)
                .where(post.isDeleted.eq(0))
                .where(post.display.eq(1))
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
                .set(post.isDeleted, postEditDto.getIsDeleted())
                .set(post.display, postEditDto.getDisplay())
                .where(post.id.eq(postId))
                .execute();
        em.clear();
    }

    @Override
    @CacheEvict(value = "thumbnailImage", allEntries = true)
    public Page<Post> getPostListByPage(GetPostPageReq req, Pageable pageable) {
        JPAQuery<Post> query = jpaQueryFactory.selectFrom(post).leftJoin(post.comments).fetchJoin().where(post.isDeleted.eq(0))
                .where(post.display.eq(1)).orderBy(post.id.desc());
        super.setPageQuery(query, pageable, post);
        List<Post> result = query.fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(post.id.count())
                .from(post)
                .where(post.isDeleted.eq(0))
                .where(post.display.eq(1));
        Long count = countQuery.fetchOne();
        count = count == null ? 0 : count;
        return new PageImpl<>(result, super.getValidPageable(pageable), count);

    }

    @Override
    public void updateDeletedByPostId(Long postId) {
        jpaQueryFactory.update(post)
                .set(post.isDeleted, 1)
                .where(post.id.eq(postId))
                .execute();
        em.clear();
    }

    @Override
    public void updatePostThumbnailImage(String imagePath, Long postId) {

        jpaQueryFactory.update(post)
                .set(post.thumbnailImage, imagePath)
                .where(post.id.eq(postId))
                .execute();
        em.clear();
    }
}
