package com.ybcharlog.api.domain.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ybcharlog.api.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Session {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String accessToken;

	private String revokeToken;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	@JsonIgnore
	private User user;

	@Builder
	public Session(User user) {
		this.accessToken = UUID.randomUUID().toString();
		this.revokeToken = UUID.randomUUID().toString();
		this.user = user;
	}
}
