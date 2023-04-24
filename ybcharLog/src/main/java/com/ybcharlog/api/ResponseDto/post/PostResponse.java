package com.ybcharlog.api.ResponseDto.post;

import com.querydsl.core.annotations.QueryProjection;
import com.ybcharlog.api.domain.comment.Comment;
import com.ybcharlog.api.domain.post.Post;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 서비스 정책에 따른 응답 클래스
 */

@Builder
@Getter
@Data
public class PostResponse {

	private final Long id;
	private final String title;
	private final String content;
	private final Integer display;
	private final Integer isDeleted;
	private final Integer viewCount;
	private final Integer likeCount;
	private final String thumbnailImage;
	private final List<Comment> comments;

	private final LocalDateTime createdAt;
	private final LocalDateTime lastModifiedDate;

	// 생성자 오버로딩
	public PostResponse(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.display = post.getDisplay();
		this.isDeleted = post.getIsDeleted();
		this.viewCount = post.getViewCount();
		this.likeCount = post.getLikeCount();
		this.thumbnailImage = post.getThumbnailImage();
		this.comments = post.getComments();
		this.createdAt = post.getCreatedAt();
		this.lastModifiedDate = post.getLastModifiedDate();
	}

	@Builder
	public PostResponse(Long id, String title, String content, Integer display, Integer isDeleted, Integer viewCount, Integer likeCount,
	                    String thumbnailImage, List<Comment> comments, LocalDateTime createdAt, LocalDateTime lastModifiedDate) {
		this.id = id;
		this.title = title.substring(0, Math.min(title.length(), 10));
		this.content = content;
		this.display = display;
		this.isDeleted = isDeleted;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
		this.thumbnailImage = thumbnailImage;
		this.comments = comments;
		this.createdAt = createdAt;
		this.lastModifiedDate = lastModifiedDate;
	}
}
