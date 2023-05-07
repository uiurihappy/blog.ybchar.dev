package com.ybcharlog.api.domain.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum UserRole {
	ROLE_USER("일반 사용자"),
	ROLE_ADMIN("관리자"),
	ROLE_MASTER("마스터 관리자");

	private final String desc;

	@JsonValue
	public String getValue() {
		return this.name();
	}

	@JsonCreator
	public static UserRole of(String name) {
		for (UserRole obj : UserRole.values()) {
			if (name.equalsIgnoreCase(obj.name())) {
				return obj;
			}
		}

		throw new IllegalArgumentException(String.format("Invalid type.(%s)", name));
	}

	public static List<Map<String, String>> codes() {
		List<Map<String, String>> codes = new ArrayList<>();

		for (UserRole obj : UserRole.values()) {
			Map<String, String> map = new HashMap<>();
			map.put("name", obj.name());
			map.put("desc", obj.getDesc());
			codes.add(map);
		}

		return codes;
	}
}