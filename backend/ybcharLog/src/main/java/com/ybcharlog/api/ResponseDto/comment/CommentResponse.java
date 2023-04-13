package com.ybcharlog.api.ResponseDto.comment;

import com.ybcharlog.api.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentResponse {

    private final Long id;
    private final String username;
    private final String password;
    private final String commentContent;
    private final Integer secretStatus;

    // 생성자 오버로딩
    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.password = comment.getPassword();
        this.commentContent = comment.getCommentContent();
        this.secretStatus = comment.getSecretStatus();
    }

    @Builder
    public CommentResponse(Long id, String username, String password, String commentContent, Integer secretStatus) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.commentContent = commentContent;
        this.secretStatus = secretStatus == null ? 0 : secretStatus;
    }
}
