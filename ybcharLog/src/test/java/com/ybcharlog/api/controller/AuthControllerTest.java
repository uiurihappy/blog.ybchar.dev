package com.ybcharlog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybcharlog.api.RequestDto.auth.SignUpDto;
import com.ybcharlog.api.controller.user.AuthController;
import com.ybcharlog.api.domain.user.Role;
import com.ybcharlog.api.domain.user.User;
import com.ybcharlog.api.exception.AlreadyExistsEmailException;
import com.ybcharlog.api.repository.user.UserRepository;
import com.ybcharlog.api.service.auth.AuthService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
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
	private AuthController authController;

	@Autowired
	private AuthService authService;


	@Value("${auth.key}")
	private String authKey;

	// 다른 테스트에 영향이 가지 않도록 사전에 deleteAll한다.
	@BeforeEach
	void beforeClean() {
		userRepository.deleteAll();
	}

	@AfterEach
	void afterClean() {
		userRepository.deleteAll();
	}

	@Test
	@DisplayName("회원가입 테스트")
	void signUpTest() throws Exception {
		// given
		SignUpDto signUpDto = SignUpDto.builder()
				.nickname("tester1")
				.password("qwer1234")
				.email("ybchar@test.com")
				.role(Role.ADMIN)
				.build();

		// when
		authController.signUp(signUpDto);

		// then
		assertEquals(1, userRepository.count());

		User user = userRepository.findAll().iterator().next();
		assertEquals("tester1", user.getNickname());
		assertNotNull(user.getPassword());
		assertEquals("qwer1234", user.getPassword());
		assertEquals("ybchar@test.com", user.getEmail());
	}

	@Test
	@DisplayName("회원가입 중복 테스트")
	void signUpExistsTest() throws Exception {
		// given
		User user = User.builder()
				.email("ybchar@test.com")
				.nickname("ybchar")
				.password("qwer1234")
				.role(Role.ADMIN)
				.build();
		userRepository.save(user);

		SignUpDto signUpDto = SignUpDto.builder()
				.nickname("tester1")
				.password("qwer1234")
				.email("ybchar@test.com")
				.role(Role.ADMIN)
				.build();

		// expected
		assertThrows(AlreadyExistsEmailException.class, () -> authController.signUp(signUpDto));
	}

	@Test
	@DisplayName("회원가입 mock Test")
	void signUpMockTest() throws Exception {
		// given
		SignUpDto signUpDto = SignUpDto.builder()
				.nickname("tester1")
				.password("qwer1234")
				.email("ybchar@test.com")
				.role(Role.ADMIN)
				.build();

		// expected
		mockMvc.perform(post("/auth/join")
					.content(objectMapper.writeValueAsString(signUpDto))
					.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print());
	}


}
