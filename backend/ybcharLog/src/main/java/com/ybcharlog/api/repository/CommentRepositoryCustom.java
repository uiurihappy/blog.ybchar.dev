package com.ybcharlog.api.repository;

import com.ybcharlog.api.domain.Comment;

import java.util.List;

public interface CommentRepositoryCustom {

    List<Comment> getList(CommentSearchDto commentSearchDto);
    void editComment(Long commentId, CommentEditDto commentEditDto);
}
