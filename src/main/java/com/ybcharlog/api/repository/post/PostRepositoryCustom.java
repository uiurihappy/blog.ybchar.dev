package com.ybcharlog.api.repository.post;

import com.ybcharlog.api.RequestDto.post.PostEditDto;
import com.ybcharlog.api.RequestDto.post.PostSearchDto;
import com.ybcharlog.api.ResponseDto.post.PostResponse;
import com.ybcharlog.api.ResponseDto.post.PostResponse.GetPostPageReq;
import com.ybcharlog.api.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {

    PostResponse getPostOne(Long postId);
    List<Post> getList(PostSearchDto postSearchDto);
    void editPost(Long postId, PostEditDto postEditDto);

    Page<Post> getPostListByPage(GetPostPageReq req, Pageable pageable);

    void updateDeletedByPostId(Long postId);

    void updatePostThumbnailImage(String imagePath, Long postId);
//    @Transactional
//    @Modifying(clearAutomatically = true)
//    @Query("delete from Post p where p.id = :id")
//    void deleteByPostId(@Param("postId") Long id);
}
