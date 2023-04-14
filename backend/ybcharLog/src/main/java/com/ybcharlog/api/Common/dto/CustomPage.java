package com.ybcharlog.api.Common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomPage<T> {

	private List<T> list;
	private Long totalElements;
	private Integer totalCount;
}
