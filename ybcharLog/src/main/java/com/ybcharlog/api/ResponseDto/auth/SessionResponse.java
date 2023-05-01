package com.ybcharlog.api.ResponseDto.auth;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@Getter
public class SessionResponse {

	private final String accessToken;

	public SessionResponse(String accessToken) {
		this.accessToken = accessToken;
	}
}
