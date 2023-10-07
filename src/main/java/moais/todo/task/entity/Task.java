package moais.todo.task.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import moais.todo.member.entity.Member;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(indexes = {
		@Index(name = "idx_task_updated_at", columnList = "updatedAt"),
		@Index(name = "idx_task_member_id", columnList = "member_id")
})
@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id")
	private Long id;

	private String title;

	private String content;

	@Enumerated(EnumType.STRING)
	private Progress progress;

	@CreationTimestamp
	private LocalDateTime createdAt;

	@UpdateTimestamp
	private LocalDateTime updatedAt;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@PrePersist
	public void prePersist() {
		this.updatedAt = LocalDateTime.now();
	}

	public void change(String title, String content, Progress progress) {
		this.title = ObjectUtils.isEmpty(title) ? this.title : title;
		this.content = ObjectUtils.isEmpty(content) ? this.content : content;
		this.progress = ObjectUtils.isEmpty(progress) ? this.progress : progress;
	}

	public enum Progress {
		TODO, DOING, DONE
	}
}
