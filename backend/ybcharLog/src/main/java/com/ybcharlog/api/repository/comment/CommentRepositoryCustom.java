package com.ybcharlog.api.repository.comment;

import com.ybcharlog.api.RequestDto.comment.CommentEditDto;
import com.ybcharlog.api.RequestDto.comment.CommentSearchDto;
import com.ybcharlog.api.domain.comment.Comment;

import java.util.List;

public interface CommentRepositoryCustom {

    List<Comment> getList(CommentSearchDto commentSearchDto);
    void editComment(Long commentId, CommentEditDto commentEditDto);
}
