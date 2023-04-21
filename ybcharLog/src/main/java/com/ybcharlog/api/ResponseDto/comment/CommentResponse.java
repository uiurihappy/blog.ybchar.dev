package com.ybcharlog.api.ResponseDto.comment;

import com.ybcharlog.api.domain.comment.Comment;
import com.ybcharlog.api.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class CommentResponse {

    private final Long id;
    private final String username;
    private final String password;
    private final String commentContent;
    private final Integer secretStatus;
    private final Integer display;
    private final Integer isDeleted;

    // 생성자 오버로딩
    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.password = comment.getPassword();
        this.commentContent = comment.getCommentContent();
        this.secretStatus = comment.getSecretStatus();
        this.display = comment.getDisplay();
        this.isDeleted = comment.getIsDeleted();
    }

    @Builder
    public CommentResponse(Long id, String username, String password, String commentContent, Integer secretStatus, Integer display, Integer isDeleted, Post post) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.commentContent = commentContent;
        this.secretStatus = secretStatus == null ? 0 : secretStatus;
        this.isDeleted = isDeleted == null ? 0 : isDeleted;
        this.display = display == null ? 0 : display;
    }
}
