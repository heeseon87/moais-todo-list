package moais.todo.task.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import moais.todo.task.dto.CreateTaskReqDto;
import moais.todo.task.dto.CreateTaskResDto;
import moais.todo.task.dto.UpdateTaskReqDto;
import moais.todo.task.dto.UpdateTaskResDto;
import moais.todo.task.entity.Task;
import moais.todo.task.repository.TaskRepository;

@RequiredArgsConstructor
@Service
public class TaskService {

	private final TaskRepository taskRepository;

	public List<Task> getTasks(Long memberId) {
		return taskRepository.findAllMyTasks(memberId);
	}

	public Task get(Long taskId, Long memberId) {
		return taskRepository.findMyTask(taskId, memberId)
				.orElseThrow(() -> new IllegalArgumentException("Task not found"));
	}

	public List<Task> getRecentlyTask(Long memberId) {
		return taskRepository.findFirstByMember_IdOrderByUpdatedAtDesc(memberId);
	}

	public CreateTaskResDto save(CreateTaskReqDto dto, Long id) {
		Task save = taskRepository.save(dto.toEntity(id));
		return new CreateTaskResDto(save.getId());
	}

	@Transactional
	public UpdateTaskResDto change(Long memberId, Long taskId, UpdateTaskReqDto dto) {
		Task task = get(taskId, memberId);
		task.change(dto.title(), dto.content(), dto.progress());
		return new UpdateTaskResDto(task.getId(), task.getTitle(), task.getContent(), task.getProgress());
	}
}
