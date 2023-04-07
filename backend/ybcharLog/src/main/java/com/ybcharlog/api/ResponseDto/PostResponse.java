package com.ybcharlog.api.ResponseDto;

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

	@Builder
	public PostResponse(Long id, String title, String content, Integer viewCount) {
		this.id = id;
		this.title = title.substring(0, Math.min(title.length(), 10));
		this.content = content;
		this.viewCount = viewCount;
	}
}
