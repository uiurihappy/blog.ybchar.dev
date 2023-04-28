package com.ybcharlog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybcharlog.api.RequestDto.auth.LoginDto;
import com.ybcharlog.api.RequestDto.post.PostCreateDto;
import com.ybcharlog.api.domain.user.Role;
import com.ybcharlog.api.domain.user.User;
import com.ybcharlog.api.repository.post.PostRepository;
import com.ybcharlog.api.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	@Value("${auth.key}")
	private String authKey;

	// 다른 테스트에 영향이 가지 않도록 사전에 deleteAll한다.
	@BeforeEach
	void clean() {
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("로그인 후에 serssion 생성Test")
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

		// DB에 1개 저장
	}

}
