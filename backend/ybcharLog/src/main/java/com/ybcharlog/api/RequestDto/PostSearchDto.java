package com.ybcharlog.api.RequestDto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class PostSearchDto {

	private Integer page;
	private Integer size;

	@Builder
	public PostSearchDto(Integer page, Integer size) {
		this.page = page;
		this.size = size;
	}
}
