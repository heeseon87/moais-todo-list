package moais.todo.login.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import moais.todo.member.entity.Member;
import moais.todo.member.service.MemberService;

@SpringBootTest
@AutoConfigureMockMvc
class LoginFailureTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MemberService memberService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@DisplayName("[예외] 로그인 실패")
	@Test
	public void testLoginFailure() throws Exception {
		// Arrange
		String username = "testUsername";
		String password = "testPassword";
		Member member = new Member(username, password, null);
		member.encryptPassword(passwordEncoder);
		memberService.save(member);

		// Act & Assert
		mockMvc.perform(formLogin("/public/front/login").user(username).password(password + "wrong"))
				.andExpect(status().isFound())
				.andExpect(unauthenticated());
	}
}
