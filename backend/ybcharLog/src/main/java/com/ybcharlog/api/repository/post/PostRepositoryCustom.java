package com.ybcharlog.api.repository.post;

import com.ybcharlog.api.RequestDto.post.PostEditDto;
import com.ybcharlog.api.RequestDto.post.PostSearchDto;
import com.ybcharlog.api.ResponseDto.post.PostResponse;
import com.ybcharlog.api.domain.post.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface PostRepositoryCustom {

    Post getPostOne(Long postId);
    List<Post> getList(PostSearchDto postSearchDto);
    void editPost(Long postId, PostEditDto postEditDto);

//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query("delete from Post p where p.id = :id")
//    void deleteByPostId(@Param("postId") Long id);
}
