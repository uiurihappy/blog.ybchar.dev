package com.ybcharlog.api.dto.RequestDto.post;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

@Getter
@ToString
@NoArgsConstructor
public class PostThumbNailImageDto {

	@NotBlank(message = "이미지를 등록해주세요")
	private MultipartFile file;

	@NotBlank(message = "경로를 입력해주세요")
	private String path;

	@NotBlank(message = "PostId를 입력해주세요")
	private Long postId;

	@Builder
	public PostThumbNailImageDto(MultipartFile file, String path, Long postId) {
		this.file = file;
		this.path = path;
		this.postId = postId;
	}
}
