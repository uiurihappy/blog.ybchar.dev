package com.ybcharlog.api.domain;

import com.ybcharlog.api.Common.BaseEntity;
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
    private String comment;

    @Column(columnDefinition = "tinyint(3) not null default 0 COMMENT '비밀 댓글 상태'")
    private Integer secretStatus;

    public static Comment initComment(String username, String password, String comment, Integer secretStatus) {
        return Comment.builder().username(username).password(password).comment(comment).secretStatus(0).build();
    }

    @Builder
    public Comment(String username, String password, String comment, Integer secretStatus) {
        this.username = username;
        this.password = password;
        this.comment = comment;
        this.secretStatus = secretStatus;
    }
}
