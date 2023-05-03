package com.ybcharlog.api.repository.post;

import com.ybcharlog.api.ResponseDto.post.PostResponse.GetPostPageReq;
import com.ybcharlog.api.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom, JpaSpecificationExecutor<Post> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("delete from Post p where p.id = :postId")
    void deleteByPostId(@Param("postId") Long postId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Post p set p.isDeleted = 1 where p.id = :postId")
    void updateIsDeletedByPostId(@Param("postId") Long postId);


    @Transactional
    @Query("select p, c from Post p left join p.comments c " +
            "where p.isDeleted = 0 and p.display = 1 order by p.id desc")
    Page<Post> getPostList(GetPostPageReq req, Pageable pageable);

}
