package com.ybcharlog.api.repository.comment;

import com.ybcharlog.api.RequestDto.comment.CommentEditDto;
import com.ybcharlog.api.RequestDto.comment.CommentSearchDto;
import com.ybcharlog.api.domain.comment.Comment;

import java.util.List;

public interface CommentRepositoryCustom {

    List<Comment> getList(Long postId, CommentSearchDto commentSearchDto);
    void editComment(Long commentId, CommentEditDto commentEditDto);

}
