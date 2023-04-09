package com.ybcharlog.api.repository;

import com.ybcharlog.api.domain.Post;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(int page);
}
