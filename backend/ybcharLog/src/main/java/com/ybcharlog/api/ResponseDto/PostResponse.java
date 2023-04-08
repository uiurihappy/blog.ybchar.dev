package com.ybcharlog.api.ResponseDto;

import com.ybcharlog.api.domain.Post;
import lombok.Builder;
import lombok.Getter;

/**
 * 서비스 정책에 따른 응답 클래스
 */

@Builder
@Getter
public class PostResponse {

	private final Long id;
	private final String title;
	private final String content;
	private final Integer viewCount;
	private final Integer likeCount;

	// 생성자 오버로딩
	public PostResponse(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.viewCount = post.getViewCount();
		this.likeCount = post.getLikeCount();
	}

	@Builder
	public PostResponse(Long id, String title, String content, Integer viewCount, Integer likeCount) {
		this.id = id;
		this.title = title.substring(0, Math.min(title.length(), 10));
		this.content = content;
		this.viewCount = viewCount;
		this.likeCount = likeCount;
	}
}
