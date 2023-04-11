package com.ybcharlog.api.domain.post;

import com.ybcharlog.api.Common.BaseEntity;
import com.ybcharlog.api.RequestDto.post.PostEditDto;
import com.ybcharlog.api.domain.comment.Comment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.ybcharlog.api.domain.post.PostEditor.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "varchar(50) not null COMMENT '게시글 제목'")
	private String title;

	@Column(columnDefinition = "text not null COMMENT '게시글 내용'")
	@Lob
	private String content;

	@Column(columnDefinition = "int unsigned not null default 0 COMMENT '게시글 조회 수'")
	private Integer viewCount;

	@Column(columnDefinition = "int unsigned not null default 0 COMMENT '게시글 좋아요 수'")
	private Integer likeCount;

	@OneToMany(mappedBy = "post")
	private List<Comment> comments = new ArrayList<>();

	public static Post initPost(String title, String content) {
		return Post.builder().title(title).content(content).viewCount(0).likeCount(0).build();
	}

	@Builder
	public Post(String title, String content, Integer viewCount, Integer likeCount) {
		this.title = title;
		this.content = content;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
	}

	public void changeTitle(String title) {
		this.title = title;
	}

	public void changeContent(String content) {
		this.content = content;
	}

	public void changePost(String title, String content) {
		this.title = title;
		this.content = content;
	}

	public PostEditorBuilder toEditor() {
		return PostEditor.builder()
				.title(title)
				.content(content);
	}

	public void edit(PostEditDto postEditDto, Post post) {
		this.title = postEditDto.getTitle() != null ? postEditDto.getTitle() : post.getTitle();
		this.content = postEditDto.getContent() != null ? postEditDto.getContent() : post.getContent();
	}
}
