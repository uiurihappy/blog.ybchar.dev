package com.ybcharlog.api.service.comment;

import com.ybcharlog.api.RequestDto.comment.CommentCreateDto;
import com.ybcharlog.api.ResponseDto.comment.CommentResponse;
import com.ybcharlog.api.domain.comment.Comment;
import com.ybcharlog.api.domain.post.Post;
import com.ybcharlog.api.exception.PostNotFound;
import com.ybcharlog.api.mapper.comment.GetCommentResDtoMapper;
import com.ybcharlog.api.repository.comment.CommentRepository;
import com.ybcharlog.api.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
	private final PostRepository postRepository;

	private final CommentRepository commentRepository;

    public CommentResponse getOne(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        return CommentResponse.builder()
                .id(comment.getId())
                .username(comment.getUsername())
                .password(comment.getPassword())
                .content(comment.getContent())
                .secretStatus(comment.getSecretStatus())
		        .display(comment.getDisplay())
		        .isDeleted(comment.getIsDeleted())
                .build();
    }

    public CommentResponse write(CommentCreateDto commentCreateDto, Long postId) {
	    Post post = postRepository.findById(postId).orElseThrow(PostNotFound::new);

        return GetCommentResDtoMapper.INSTANCE.toDto(commentRepository.save(Comment.initComment(commentCreateDto.getUsername(), commentCreateDto.getPassword()
		        , commentCreateDto.getContent(), commentCreateDto.getSecretStatus(), commentCreateDto.getDisplay(), commentCreateDto.getIsDeleted(), post)));
    }

	public void deleteOneComment(Long commentId) {
		commentRepository.deleteById(commentId);
	}
}
