package com.ybcharlog.api.domain.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ybcharlog.api.Common.BaseEntity;
import com.ybcharlog.api.ResponseDto.comment.CommentResponse;
import com.ybcharlog.api.domain.post.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(20) not null COMMENT '댓글 사용자 이름'")
    private String username;

    @Column(columnDefinition = "varchar(15) COMMENT '댓글 비밀번호'")
    private String password;

    @Column(columnDefinition = "text not null COMMENT '댓글 내용'")
    private String commentContent;

    @Column(columnDefinition = "tinyint(3) not null default 1 COMMENT '노출 상태'")
    private Integer display;

    @Column(columnDefinition = "tinyint(3) not null default 0 COMMENT '삭제 상태'")
    private Integer isDeleted;

    @Column(columnDefinition = "tinyint(3) not null default 0 COMMENT '비밀 댓글 상태'")
    private Integer secretStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "postId")
    @JsonIgnore
    private Post post;

    public static Comment initComment(String username, String password, String commentContent, Integer secretStatus, Integer display, Integer isDeleted, Post post) {
        secretStatus = password == null ? 0 : secretStatus;
        display = display == null ? 0 : display;
        isDeleted = (isDeleted == 1 || isDeleted == null) ? 0 : display;
        return Comment.builder().username(username).password(password).commentContent(commentContent)
                .secretStatus(secretStatus).display(display).isDeleted(isDeleted).post(post).build();
    }

    @Builder
    public Comment(String username, String password, String commentContent, Integer secretStatus, Integer display, Integer isDeleted, Post post) {
        this.username = username;
        this.password = password;
        this.commentContent = commentContent;
        this.secretStatus = secretStatus;
        this.display = display;
        this.isDeleted = isDeleted;
        this.post = post;
    }
}
