package moais.todo.task.dto;

import moais.todo.member.entity.Member;
import moais.todo.task.entity.Task;
import moais.todo.task.entity.Task.Progress;

public record CreateTaskReqDto(String title, String content, Progress progress) {

	public Task toEntity(Long memberId) {
		return Task.builder()
				.title(title)
				.content(content)
				.progress(progress)
				.member(new Member(memberId))
				.build();
	}
}
