package com.ybcharlog.api.repository.post;

import com.ybcharlog.api.domain.post.Post;
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
}
