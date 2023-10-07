package moais.todo.signup.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import moais.todo.member.entity.Member;
import moais.todo.member.repository.MemberRepository;
import moais.todo.member.service.MemberService;
import moais.todo.signup.dto.SignupReq;

@DataJpaTest
@Import({SignupService.class, MemberService.class, BCryptPasswordEncoder.class})
class SignupServiceTest {

	@Autowired
	private SignupService signupService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@DisplayName("[정상] 회원 가입(비밀번호 암호화)")
	@Test
	public void signup() {
		// Arrange
		SignupReq req = new SignupReq();
		req.setUsername("testUsername");
		req.setPassword("testPassword");

		// Act
		signupService.signup(req);

		// Assert
		Member member = memberRepository.findByUsername(req.getUsername())
				.orElseThrow(() -> new AssertionError("Member should be saved"));
		assertThat(member.getUsername()).isEqualTo(req.getUsername());
		assertThat(passwordEncoder.matches(req.getPassword(), member.getPassword())).isTrue();
	}

	@DisplayName("[예외] 이미 존재하는 username")
	@Test
	public void signupWithExistingUsername() {
		// Arrange
		SignupReq req = new SignupReq();
		req.setUsername("testUsername");
		req.setPassword("testPassword");
		signupService.signup(req);

		// Act & Assert
		assertThatThrownBy(() -> signupService.signup(req))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("이미 존재하는 유저입니다.");
	}

}
