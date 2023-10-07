package moais.todo.task.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import moais.todo.security.conf.MyUserDetails;
import moais.todo.task.service.TaskService;

@RequiredArgsConstructor
@Controller
public class TaskFrontController {

	private final TaskService taskService;

	@GetMapping("/auth/front/tasks")
	public String showTaskPage(@AuthenticationPrincipal MyUserDetails userDetails) {
		taskService.getTasks(userDetails.getId());
		return "task";
	}
}
