package com.ybcharlog.api.repository.comment;

import com.ybcharlog.api.RequestDto.comment.CommentEditDto;
import com.ybcharlog.api.RequestDto.comment.CommentSearchDto;
import com.ybcharlog.api.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface CommentRepositoryCustom {

    List<Comment> getList(Long postId, CommentSearchDto commentSearchDto);
    void editComment(Long commentId, CommentEditDto commentEditDto);

}
