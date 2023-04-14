package com.ybcharlog.api.service.comment;

import com.ybcharlog.api.RequestDto.comment.CommentCreateDto;
import com.ybcharlog.api.ResponseDto.comment.CommentResponse;
import com.ybcharlog.api.domain.comment.Comment;
import com.ybcharlog.api.repository.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CommentResponse getOne(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        return CommentResponse.builder()
                .id(comment.getId())
                .username(comment.getUsername())
                .password(comment.getPassword())
                .commentContent(comment.getCommentContent())
                .secretStatus(comment.getSecretStatus())
                .build();
    }

    public Comment write(CommentCreateDto commentCreateDto) {
        return commentRepository.save(Comment.initComment(commentCreateDto.getUsername(), commentCreateDto.getPassword(), commentCreateDto.getCommentContent(), commentCreateDto.getSecretStatus()));
    }

	public void deleteOneComment(Long commentId) {
		commentRepository.deleteById(commentId);
	}
}
