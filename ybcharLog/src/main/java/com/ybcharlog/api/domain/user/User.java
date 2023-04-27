package com.ybcharlog.api.domain.user;

import com.ybcharlog.api.Common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

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

	@Column(columnDefinition = "varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '사용자 비밀번호'")
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null default 'USER' COMMENT '사용자 권한'")
	private Role role;

	@Builder
	public User(String email, String nickname, String password, Role role) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.role = role;
	}
}
