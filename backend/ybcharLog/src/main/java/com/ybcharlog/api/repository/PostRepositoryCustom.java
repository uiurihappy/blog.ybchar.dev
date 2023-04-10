package com.ybcharlog.api.repository;

import com.ybcharlog.api.RequestDto.PostEditDto;
import com.ybcharlog.api.RequestDto.PostSearchDto;
import com.ybcharlog.api.domain.Post;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearchDto postSearchDto);
    void editPost(Long postId, PostEditDto postEditDto);
}
