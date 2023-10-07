package moais.todo.member.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moais.todo.task.entity.Task;

@Getter
@Setter
@Entity
@Table
@NoArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String username;

	private String password;

	private String nickname;

	private String role = "ROLE_USER";

	@JsonManagedReference
	@OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Task> tasks = new ArrayList<>();

	public Member(Long id) {
		this.id = id;
	}

	public Member(String username, String password, String nickname) {
		this.username = username;
		this.nickname = nickname;
		this.password = password;
	}

	public void encryptPassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(password);
	}
}
