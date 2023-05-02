package com.ybcharlog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybcharlog.api.RequestDto.auth.LoginDto;
import com.ybcharlog.api.RequestDto.auth.SignUpDto;
import com.ybcharlog.api.RequestDto.post.PostCreateDto;
import com.ybcharlog.api.controller.user.AuthController;
import com.ybcharlog.api.crypto.PasswordEncoder;
import com.ybcharlog.api.domain.auth.Session;
import com.ybcharlog.api.domain.user.Role;
import com.ybcharlog.api.domain.user.User;
import com.ybcharlog.api.exception.AlreadyExistsEmailException;
import com.ybcharlog.api.exception.InvalidSigninInformation;
import com.ybcharlog.api.exception.UserNotFound;
import com.ybcharlog.api.repository.user.SessionRepository;
import com.ybcharlog.api.repository.user.UserRepository;
import com.ybcharlog.api.service.auth.AuthService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
	@DisplayName("로그인 성공")
	void signInTest() throws Exception {
		// given
		PasswordEncoder encoder = new PasswordEncoder();
		String encryptPassword = encoder.encrypt("qwer1234");
		User user = User.builder()
				.email("ybchar@test.com")
				.password(encryptPassword)
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
		PasswordEncoder encoder = new PasswordEncoder();
		String encryptPassword = encoder.encrypt("qwer1234");
		User user = User.builder()
				.email("ybchar@test.com")
				.password(encryptPassword)
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

		assertEquals(0, user.getSessions().size());
	}

	@Test
	@DisplayName("로그인 후 검증되지 않은 세션값으로 권한이 필요한 페이지에 접속할 수 없다.")
	void NotValidationSessionValueIsAccess() throws Exception {
		// given
		PasswordEncoder encoder = new PasswordEncoder();
		String encryptPassword = encoder.encrypt("qwer1234");
		User user = User.builder()
				.email("ybchar@test.com")
				.password(encryptPassword)
				.nickname("hi")
				.role(Role.valueOf("ADMIN"))
				.build();
		Session session = user.addSession();
		userRepository.save(user);

		// expected
		mockMvc.perform(get("/posts/authTest")
						.header("authorization", session.getAccessToken() + "tewtg")
						.contentType(APPLICATION_JSON)
				)
				.andExpect(status().isUnauthorized())
				.andDo(print());
	}

	@Test
	@DisplayName("회원가입 테스트")
	void signUpTest() throws Exception {
		// given
		PasswordEncoder encoder = new PasswordEncoder();
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
		assertTrue(encoder.matches("qwer1234", user.getPassword()));
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

	@Test
	@DisplayName("로그인 서비스 테스트")
	void signInEncryptTest() throws Exception {
		// given
		SignUpDto signUpDto = SignUpDto.builder()
				.nickname("tester1")
				.password("qwer1234")
				.email("ybchar@test.com")
				.role(Role.ADMIN)
				.build();
		authController.signUp(signUpDto);
		LoginDto loginDto = LoginDto.builder()
				.email("ybchar@test.com")
				.password("qwer1234")
				.build();

		// when
		Long userId = authService.signIn(loginDto);

		// then
		assertNotNull(userId);

	}

	@Test
	@DisplayName("비밀번호 틀린 케이스 테스트")
	void wrongPasswordTest() throws Exception {
		// given
		PasswordEncoder encoder = new PasswordEncoder();
		String encryptPassword = encoder.encrypt("qwer1234");

		SignUpDto signUpDto = SignUpDto.builder()
				.nickname("tester1")
				.password(encryptPassword)
				.email("ybchar@test.com")
				.role(Role.ADMIN)
				.build();
		authController.signUp(signUpDto);
		LoginDto loginDto = LoginDto.builder()
				.email("ybchar@test.com")
				.password("1234")
				.build();

		// when
		assertThrows(InvalidSigninInformation.class, () -> authService.signIn(loginDto));

	}


}
