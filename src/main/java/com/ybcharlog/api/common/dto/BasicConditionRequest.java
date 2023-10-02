package com.ybcharlog.api.common.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BasicConditionRequest {

	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private String search;
}
