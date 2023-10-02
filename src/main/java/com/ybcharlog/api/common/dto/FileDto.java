package com.ybcharlog.api.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class FileDto {
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class UploadFileRes {

		private Long id;
		private String name;
		private String url;
	}
}
