package com.ybcharlog.api.domain.user;

import com.ybcharlog.api.Common.BaseTimeEntity;
import com.ybcharlog.api.Common.converter.UserRoleConverter;
import lombok.*;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
	private List<Role> role = new ArrayList<>();

	@Builder
	public User(String email, String nickname, String password, List<Role> role) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.role = role;
	}

	public static User initEmailUser(String email, String password, String nickname) {
		return User.builder()
				.email(email)
				.password(password)
				.nickname(nickname)
				.role(List.of(Role.USER))
				.build();
	}
}
