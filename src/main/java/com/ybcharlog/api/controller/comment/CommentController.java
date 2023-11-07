package com.ybcharlog.api.controller.comment;

import com.ybcharlog.api.dto.RequestDto.comment.CommentCreateDto;
import com.ybcharlog.api.dto.ResponseDto.comment.CommentResponse;
import com.ybcharlog.api.service.comment.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 댓글 단건 조회
    @GetMapping("/comments/{commentId}")
    public CommentResponse getOne(@PathVariable Long commentId) {
        return commentService.getOne(commentId);
    }

    // 댓글 등록
    @PostMapping("/posts/{postId}/comments")
    public CommentResponse save(@RequestBody @Valid CommentCreateDto request, @PathVariable Long postId) {
        return commentService.write(request, postId);
    }

    // 댓글 단건 삭제
    @DeleteMapping("/delete/{commentId}")
    public void deleteOneComment(@PathVariable Long commentId) {
        commentService.deleteOneComment(commentId);
    }
}
