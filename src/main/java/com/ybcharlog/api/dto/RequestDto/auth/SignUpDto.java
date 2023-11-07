package com.ybcharlog.api.dto.RequestDto.auth;

import lombok.*;

@Getter
@Data
@NoArgsConstructor
public class SignUpDto {

    private String email;
    private String nickname;
    private String password;
    private String roles;

    @Builder
    public SignUpDto(String email, String nickname, String password, String roles) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.roles = roles;
    }

}
