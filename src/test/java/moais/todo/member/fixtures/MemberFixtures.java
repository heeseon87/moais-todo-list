package moais.todo.member.fixtures;

import moais.todo.member.entity.Member;

public class MemberFixtures {

	public static Member createBy(String username) {
		Member member = new Member();
		member.setId(1L);
		member.setUsername(username);
		member.setPassword("testPassword");
		member.setRole("ROLE_USER");

		return member;
	}

	public static Member create() {
		Member member = new Member();
		member.setId(1L);
		member.setUsername("testUsername");
		member.setPassword("testPassword");
		member.setRole("ROLE_USER");

		return member;
	}
}
