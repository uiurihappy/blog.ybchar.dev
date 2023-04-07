package com.ybcharlog.api.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "varchar(50) not null COMMENT '게시글 제목'")
	private String title;

	@Column(columnDefinition = "text not null COMMENT '게시글 내용'")
	@Lob
	private String content;

	@Column(columnDefinition = "int unsigned default 0 COMMENT '게시글 조회 수'")
	private Integer viewCount;

	public static Post initPost(String title, String content) {
		return Post.builder().title(title).content(content).viewCount(0).build();
	}

	@Builder
	public Post(String title, String content, Integer viewCount) {
		this.title = title;
		this.content = content;
		this.viewCount = viewCount;
	}
}
