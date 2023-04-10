package com.ybcharlog.api.RequestDto;

import com.ybcharlog.api.domain.PostEditor;
import com.ybcharlog.api.domain.PostEditor.PostEditorBuilder;
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
