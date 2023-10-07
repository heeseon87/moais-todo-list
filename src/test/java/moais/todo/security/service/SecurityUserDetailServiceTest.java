package moais.todo.security.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import moais.todo.member.entity.Member;
import moais.todo.member.fixtures.MemberFixtures;
import moais.todo.member.service.MemberService;
import moais.todo.security.conf.MyUserDetails;

@DisplayName("시큐리티 유저 디테일 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class SecurityUserDetailServiceTest {

	@Mock
	private MemberService memberService;

	@InjectMocks
	private SecurityUserDetailService userDetailsService;

	@DisplayName("[정상]")
	@Test
	void testLoadUserByUsername() {
		// Arrange
		String username = "testUsername";
		Member member = MemberFixtures.createBy(username);
		when(memberService.getMember(username)).thenReturn(member);

		// Act
		MyUserDetails userDetails = (MyUserDetails)userDetailsService.loadUserByUsername(username);

		// Assert
		assertThat(member.getId()).isEqualTo(userDetails.getId());
		assertThat(member.getUsername()).isEqualTo(userDetails.getUsername());
		assertThat(member.getPassword()).isEqualTo(userDetails.getPassword());
		assertThat(Set.of(new SimpleGrantedAuthority(member.getRole()))).isEqualTo(userDetails.getAuthorities());
	}

	@DisplayName("[예외] 유저가 없는 경우 UsernameNotFoundException 발생")
	@Test
	void testLoadUserByUsernameThrowsUsernameNotFoundException() {
		// Arrange
		String username = "testUsername";
		when(memberService.getMember(username)).thenThrow(new IllegalArgumentException());

		// Act & Assert
		assertThatThrownBy(() -> {
			userDetailsService.loadUserByUsername(username);
		}).isInstanceOf(UsernameNotFoundException.class);
	}
}
