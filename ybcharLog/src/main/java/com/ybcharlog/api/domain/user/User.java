package com.ybcharlog.api.domain.user;

import com.ybcharlog.api.Common.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

	@Id @Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 45, unique = true)
	private String email;

	@Column(length = 45)
	private String nickname;

	@Column(length = 100)
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Builder
	public User(String email, String nickname, String password, Role role) {
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.role = role;
	}
}
