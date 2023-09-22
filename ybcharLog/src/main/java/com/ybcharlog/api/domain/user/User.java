package com.ybcharlog.api.domain.user;

import com.ybcharlog.api.common.BaseTimeEntity;
import com.ybcharlog.api.common.converter.UserRoleConverter;
import lombok.*;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {

	@Id @Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, columnDefinition = "varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '사용자 이메일'")
	private String email;

	@Column(columnDefinition = "varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '사용자 닉네임'")
	private String nickname;

	@Column(columnDefinition = "text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '사용자 비밀번호'")
	@Lob
	private String password;

	@Column(columnDefinition = "tinytext not null COMMENT '권한'")
	@Builder.Default
	@Convert(converter = UserRoleConverter.class)
	private List<UserRole> roles = new ArrayList<>();

	@Builder
	public User(String email, String nickname, String password, List<UserRole> roles) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.roles = roles;
	}

	public static User initEmailUser(String email, String password, String nickname, String roles) {
		String role = "ROLE_ADMIN";
		if (!role.equals(roles))
			role = "ROLE_USER";

		return User.builder()
				.email(email)
				.password(password)
				.nickname(nickname)
				.roles(List.of(UserRole.valueOf(roles)))
//				.roles(List.of(UserRole.ROLE_USER))
				.build();
	}

	public String rolesToString() {
		return this.roles.stream().map(UserRole::getValue)
				.collect(Collectors.joining(","));
	}
}
