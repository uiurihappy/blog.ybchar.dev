package com.ybcharlog.api.ResponseDto.auth;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@Getter
public class SessionResponse {

	private final String accessToken;
	private final String revokeToken;

	public SessionResponse(String accessToken, String revokeToken) {
		this.accessToken = accessToken;
		this.revokeToken = revokeToken;
	}
}
