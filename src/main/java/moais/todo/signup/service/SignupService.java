package moais.todo.signup.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import moais.todo.member.entity.Member;
import moais.todo.member.service.MemberService;
import moais.todo.signup.dto.SignupReq;

@RequiredArgsConstructor
@Service
public class SignupService {

	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;

	public void signup(SignupReq req) {
		Member member = req.toEntity();
		member.encryptPassword(passwordEncoder);

		memberService.save(member);
	}
}
