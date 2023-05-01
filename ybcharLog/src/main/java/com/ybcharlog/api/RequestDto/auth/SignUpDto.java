package com.ybcharlog.api.RequestDto.auth;

import com.ybcharlog.api.domain.user.Role;
import lombok.*;

@Getter
@Data
@NoArgsConstructor
public class SignUpDto {

    private String email;
    private String nickname;
    private String password;
    private Role role;

    @Builder
    public SignUpDto(String email, String nickname, String password, Role role) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

}
