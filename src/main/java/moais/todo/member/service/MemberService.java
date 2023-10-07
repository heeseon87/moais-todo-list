package moais.todo.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import moais.todo.member.entity.Member;
import moais.todo.member.repository.MemberRepository;

@RequiredArgsConstructor
@Service
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	public Member save(Member member) {
		memberRepository.findByUsername(member.getUsername()).ifPresent(m -> {
			throw new IllegalArgumentException("이미 존재하는 유저입니다.");
		});
		return memberRepository.save(member);
	}

	public Member getMember(String username) {
		return memberRepository.findByUsername(username)
				.orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
	}

	public void delete(Long memberId) {
		memberRepository.delete(new Member(memberId));
	}
}
