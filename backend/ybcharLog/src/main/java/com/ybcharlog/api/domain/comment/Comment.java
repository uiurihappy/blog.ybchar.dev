package com.ybcharlog.api.domain.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ybcharlog.api.Common.BaseEntity;
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

    @Column(columnDefinition = "varchar(15) COMMENT '댓글 비밀글'")
    private String password;

    @Column(columnDefinition = "text not null COMMENT '댓글 내용'")
    private String commentContent;

    @Column(columnDefinition = "tinyint(3) not null default 0 COMMENT '비밀 댓글 상태'")
    private Integer secretStatus;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    public static Comment initComment(String username, String password, String commentContent, Integer secretStatus) {
        return Comment.builder().username(username).password(password).commentContent(commentContent).secretStatus(0).build();
    }

    @Builder
    public Comment(String username, String password, String commentContent, Integer secretStatus) {
        this.username = username;
        this.password = password;
        this.commentContent = commentContent;
        this.secretStatus = secretStatus;
    }
}
