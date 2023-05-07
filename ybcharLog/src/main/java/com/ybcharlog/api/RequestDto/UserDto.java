package com.ybcharlog.api.RequestDto;

import com.ybcharlog.api.Common.util.valid.UserNicknameValid;
import com.ybcharlog.api.Common.util.valid.UserPasswordValid;
import com.ybcharlog.api.domain.user.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.List;

public class UserDto {
    @Data
    public static class SignUpReq {

        @NotBlank
        private String email;

        @NotBlank
        @UserPasswordValid
        private String password;

        @NotBlank
        @UserNicknameValid
        private String nickname;

        private String role;
    }
    @Data
    public static class SignInReq {

        @NotBlank
        private String email;

        @NotBlank
        @UserPasswordValid
        private String password;

        private Boolean remember = false;
    }

    @Data
    @Builder
    public static class SignInRes {

        private String accessToken;

        private String refreshToken;

        private Long expiresIn;
        private Long refreshExpiresIn;

        private List<UserRole> userRoles;
    }
}
