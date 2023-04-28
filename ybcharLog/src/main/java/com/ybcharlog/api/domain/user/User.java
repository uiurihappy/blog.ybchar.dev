package com.ybcharlog.api.domain.user;

import com.ybcharlog.api.Common.BaseTimeEntity;
import com.ybcharlog.api.domain.auth.Session;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Session> sessions = new ArrayList<>();

	@Builder
	public User(String email, String nickname, String password, Role role) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.role = role;
	}

	public Session addSession() {
		Session session = Session.builder()
				.user(this)
				.build();
		sessions.add(session);
		return session;
	}
}
