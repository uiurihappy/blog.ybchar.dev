package com.ybcharlog.api.ResponseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 서비스 정책에 따른 응답 클래스
 */

@Builder
@Getter
public class PostResponse {

	private final Long id;
	private final String title;
	private final String content;

	@Builder
	public PostResponse(Long id, String title, String content) {
		this.id = id;
		this.title = title.substring(0, Math.min(title.length(), 10));
		this.content = content;
	}
}
