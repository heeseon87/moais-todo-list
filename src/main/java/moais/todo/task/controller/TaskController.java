package moais.todo.task.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import moais.todo.security.conf.MyUserDetails;
import moais.todo.task.dto.CreateTaskReqDto;
import moais.todo.task.dto.UpdateTaskReqDto;
import moais.todo.task.dto.UpdateTaskResDto;
import moais.todo.task.entity.Task;
import moais.todo.task.service.TaskService;

@RequestMapping("/auth/tasks")
@RequiredArgsConstructor
@RestController
public class TaskController {

	private final TaskService taskService;

	@PostMapping
	public ResponseEntity<Object> createTask(
			@AuthenticationPrincipal MyUserDetails userDetails,
			@RequestBody CreateTaskReqDto dto) {

		var resDto = taskService.save(dto, userDetails.getId());
		return ResponseEntity.created(URI.create("/tasks/" + resDto.id())).build();
	}

	@GetMapping
	public List<Task> showTasks(@AuthenticationPrincipal MyUserDetails userDetails) {
		List<Task> tasks = taskService.getTasks(userDetails.getId());
		return tasks;
	}

	@GetMapping("/{id}")
	public Task showTask(
			@AuthenticationPrincipal MyUserDetails userDetails,
			@PathVariable("id") Long taskId) {

		return taskService.get(taskId, userDetails.getId());
	}

	@GetMapping("/recently")
	public ResponseEntity<List<Task>> showRecentlyTask(@AuthenticationPrincipal MyUserDetails userDetails) {
		List<Task> recentlyTask = taskService.getRecentlyTask(userDetails.getId());
		return ResponseEntity.ok(recentlyTask);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<UpdateTaskResDto> updateTask(
			@AuthenticationPrincipal MyUserDetails userDetails,
			@PathVariable("id") Long taskId,
			@RequestBody UpdateTaskReqDto dto) {

		UpdateTaskResDto change = taskService.change(userDetails.getId(), taskId, dto);
		return ResponseEntity.ok().body(change);
	}
}
