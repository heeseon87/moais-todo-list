package moais.todo.signup.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import moais.todo.member.entity.Member;

@ToString
@Getter
@Setter
public class SignupReq {

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	@NotBlank
	private String nickname;

	public Member toEntity() {
		return new Member(username, password, nickname);
	}
}
