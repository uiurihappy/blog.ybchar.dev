package com.ybcharlog.api.RequestDto.post;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostEditDto {

	@NotBlank(message = "타이틀을 입력해주세요.")
	private String title;

	@NotBlank(message = "글 내용을 입력해주세요.")
	private String content;

	@Builder
	public PostEditDto(String title, String content) {
		this.title = title;
		this.content = content;
	}

}
