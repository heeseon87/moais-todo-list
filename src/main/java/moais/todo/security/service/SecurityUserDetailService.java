package moais.todo.security.service;

import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moais.todo.member.entity.Member;
import moais.todo.member.service.MemberService;
import moais.todo.security.conf.MyUserDetails;

@RequiredArgsConstructor
@Service
public class SecurityUserDetailService implements UserDetailsService {

	private final MemberService memberService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Member member = memberService.getMember(username);
			var roles = Set.of(new SimpleGrantedAuthority(member.getRole()));
			return new MyUserDetails(member.getId(), member.getUsername(), member.getPassword(), roles);
		} catch (IllegalArgumentException e) {
			throw new UsernameNotFoundException(username);
		}
	}
}
