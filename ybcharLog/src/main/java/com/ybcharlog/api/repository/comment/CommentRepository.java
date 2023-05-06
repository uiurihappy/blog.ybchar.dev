package com.ybcharlog.api.repository.comment;

import com.ybcharlog.api.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom, JpaSpecificationExecutor<Comment> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("delete from Comment c where c.id in :ids")
    void deleteAllByCommentInQuery(@Param("ids") List<Long> ids);
}
