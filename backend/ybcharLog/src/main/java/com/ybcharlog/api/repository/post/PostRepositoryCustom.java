package com.ybcharlog.api.repository.post;

import com.ybcharlog.api.RequestDto.post.PostEditDto;
import com.ybcharlog.api.RequestDto.post.PostSearchDto;
import com.ybcharlog.api.domain.post.Post;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearchDto postSearchDto);
    void editPost(Long postId, PostEditDto postEditDto);
}
