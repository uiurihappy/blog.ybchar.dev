package com.ybcharlog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybcharlog.api.RequestDto.auth.LoginDto;
import com.ybcharlog.api.RequestDto.post.PostCreateDto;
import com.ybcharlog.api.domain.user.Role;
import com.ybcharlog.api.domain.user.User;
import com.ybcharlog.api.exception.UserNotFound;
import com.ybcharlog.api.repository.user.SessionRepository;
import com.ybcharlog.api.repository.user.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SessionRepository sessionRepository;

	@Value("${auth.key}")
	private String authKey;

	// 다른 테스트에 영향이 가지 않도록 사전에 deleteAll한다.
	@BeforeEach
	void clean() {
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("로그인 성공")
	void signInTest() throws Exception {
		// given
		User user = User.builder()
				.email("ybchar@test.com")
				.password("qwer1234")
				.nickname("hi")
				.role(Role.valueOf("ADMIN"))
				.build();
		userRepository.save(user);
		LoginDto loginDto = LoginDto.builder()
				.email("ybchar@test.com")
				.password("qwer1234")
				.build();

		String json = objectMapper.writeValueAsString(loginDto);
		// expected
		mockMvc.perform(post("/auth/login")
						.contentType(APPLICATION_JSON)
						.content(json)
				)
				.andExpect(status().isOk())
//				.andExpect(content().string(json))
				.andDo(print());

	}

	@Test
	@DisplayName("로그인 성공 후 session 생성")
	void signInAfterSessionSave() throws Exception {
		// given
		User user = User.builder()
				.email("ybchar@test.com")
				.password("qwer1234")
				.nickname("hi")
				.role(Role.valueOf("ADMIN"))
				.build();
		userRepository.save(user);

		LoginDto loginDto = LoginDto.builder()
				.email("ybchar@test.com")
				.password("qwer1234")
				.build();

		String json = objectMapper.writeValueAsString(loginDto);
		// expected
		mockMvc.perform(post("/auth/login")
						.contentType(APPLICATION_JSON)
						.content(json)
				)
				.andExpect(status().isOk())
//				.andExpect(content().string(json))
				.andDo(print());

		User loggedInUserId = userRepository.findById(user.getId())
						.orElseThrow(UserNotFound::new);

		assertEquals(1L, user.getSessions().size());
	}

	@Test
	@DisplayName("세션 객체 생성 후 응답 테스트")
	void signInAfterSessionSaveResponse() throws Exception {
		// given
		User user = User.builder()
				.email("ybchar@test.com")
				.password("qwer1234")
				.nickname("hi")
				.role(Role.valueOf("ADMIN"))
				.build();
		userRepository.save(user);

		LoginDto loginDto = LoginDto.builder()
				.email("ybchar@test.com")
				.password("qwer1234")
				.build();

		String json = objectMapper.writeValueAsString(loginDto);
		// expected
		mockMvc.perform(post("/auth/login")
						.contentType(APPLICATION_JSON)
						.content(json)
				)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.accessToken", Matchers.notNullValue()))
				.andDo(print());

	}

}
