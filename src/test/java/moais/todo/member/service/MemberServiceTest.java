package moais.todo.member.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import moais.todo.member.entity.Member;
import moais.todo.member.fixtures.MemberFixtures;
import moais.todo.member.repository.MemberRepository;

@DataJpaTest
@Import(MemberService.class)
class MemberServiceTest {

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberRepository memberRepository;

	@DisplayName("[정상] 회원 저장")
	@Test
	public void testSave() {
		// Arrange
		Member member = MemberFixtures.create();

		// Act
		Member savedMember = memberService.save(member);

		// Assert
		assertThat(savedMember.getId()).isNotNull();
		assertThat(savedMember.getUsername()).isEqualTo(member.getUsername());
		assertThat(savedMember.getPassword()).isEqualTo(member.getPassword());
	}

	@DisplayName("[정상] 회원 조회")
	@Test
	public void testGetMember() {
		// Arrange
		String username = "testUsername";
		Member member = MemberFixtures.createBy(username);
		memberRepository.save(member);

		// Act
		Member foundMember = memberService.getMember(username);

		// Assert
		assertThat(foundMember.getUsername()).isEqualTo(username);
	}

	@DisplayName("[예외] 없는 회원 조회")
	@Test
	public void testGetMemberThrowsException() {
		// Arrange
		String username = "nonexistentUsername";

		// Act & Assert
		assertThatThrownBy(() -> {
			memberService.getMember(username);
		}).isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("해당 유저가 없습니다.");
	}

	@DisplayName("[정상] 회원 삭제")
	@Test
	public void testDelete() {
		// Arrange
		Member savedMember = memberRepository.save(MemberFixtures.create());
		Long memberId = savedMember.getId();

		// Act
		memberService.delete(memberId);

		// Assert
		assertThat(memberRepository.findById(memberId)).isNotPresent();
	}
}
