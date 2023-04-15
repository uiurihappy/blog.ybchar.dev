package com.ybcharlog.api.mapper.post;

import com.ybcharlog.api.ResponseDto.post.PostResponse;
import com.ybcharlog.api.domain.comment.Comment;
import com.ybcharlog.api.domain.post.Post;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-15T15:45:50+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class GetPostResDtoMapperImpl implements GetPostResDtoMapper {

    @Override
    public Post toEntity(PostResponse arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Post.PostBuilder post = Post.builder();

        post.title( arg0.getTitle() );
        post.content( arg0.getContent() );
        post.viewCount( arg0.getViewCount() );
        post.likeCount( arg0.getLikeCount() );
        List<Comment> list = arg0.getComments();
        if ( list != null ) {
            post.comments( new ArrayList<Comment>( list ) );
        }

        return post.build();
    }

    @Override
    public void updateEntity(PostResponse arg0, Post arg1) {
        if ( arg0 == null ) {
            return;
        }

        if ( arg1.getComments() != null ) {
            arg1.getComments().clear();
            List<Comment> list = arg0.getComments();
            if ( list != null ) {
                arg1.getComments().addAll( list );
            }
        }
    }

    @Override
    public PostResponse toDto(Post entity) {
        if ( entity == null ) {
            return null;
        }

        PostResponse.PostResponseBuilder postResponse = PostResponse.builder();

        postResponse.id( entity.getId() );
        postResponse.title( entity.getTitle() );
        postResponse.content( entity.getContent() );
        postResponse.viewCount( entity.getViewCount() );
        postResponse.likeCount( entity.getLikeCount() );
        List<Comment> list = entity.getComments();
        if ( list != null ) {
            postResponse.comments( new ArrayList<Comment>( list ) );
        }

        return postResponse.build();
    }
}
