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
    private final String comment;
    private final Integer secretStatus;

    // 생성자 오버로딩
    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.password = comment.getPassword();
        this.comment = comment.getComment();
        this.secretStatus = comment.getSecretStatus();
    }

    @Builder
    public CommentResponse(Long id, String username, String password, String comment, Integer secretStatus) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.comment = comment;
        this.secretStatus = secretStatus == null ? 0 : secretStatus;
    }
}
