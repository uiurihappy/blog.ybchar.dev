package com.ybcharlog.api.RequestDto.auth;

import com.ybcharlog.api.domain.user.UserRole;
import lombok.*;

@Getter
@Data
@NoArgsConstructor
public class SignUpDto {

    private String email;
    private String nickname;
    private String password;
    private UserRole userRole;

    @Builder
    public SignUpDto(String email, String nickname, String password, UserRole userRole) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.userRole = userRole;
    }

}
