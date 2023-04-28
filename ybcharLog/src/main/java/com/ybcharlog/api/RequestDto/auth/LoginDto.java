package com.ybcharlog.api.RequestDto.auth;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoginDto {

	@NotBlank(message = "이메일을 입력해주세요")
	private String email;
	@NotBlank(message = "비밀번호를 입력해주세요")
	private String password;

	@Builder
	public LoginDto(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
