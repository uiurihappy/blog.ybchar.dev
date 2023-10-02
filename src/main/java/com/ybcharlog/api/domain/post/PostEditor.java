package com.ybcharlog.api.domain.post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostEditor {

	private final String title;
	private final String content;
	private final Integer display;
	private final Integer isDeleted;

	@Builder
	public PostEditor(String title, String content, Integer display, Integer isDeleted) {
		this.title = title;
		this.content = content;
		this.display = display;
		this.isDeleted = isDeleted;
	}
}
