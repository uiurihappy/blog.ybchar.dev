package com.ybcharlog.api.dto.RequestDto.post;

import lombok.*;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostEditDto {

	@NotBlank(message = "타이틀을 입력해주세요.")
	private String title;

	@NotBlank(message = "글 내용을 입력해주세요.")
	private String content;

	private Integer display;
	private Integer isDeleted;

	@Builder
	public PostEditDto(String title, String content,Integer display, Integer isDeleted) {
		this.title = title;
		this.content = content;
		this.display = display;
		this.isDeleted = isDeleted;
	}

}
