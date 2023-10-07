package moais.todo.signout.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import moais.todo.member.service.MemberService;
import moais.todo.security.conf.MyUserDetails;
import moais.todo.task.repository.TaskRepository;

@RequiredArgsConstructor
@Controller
public class SignoutController {

	private final MemberService memberService;
	private final TaskRepository taskRepository;

	@Transactional
	@PostMapping("/auth/signout")
	public String signout(@AuthenticationPrincipal MyUserDetails userDetails) {
		taskRepository.deleteAllBy(userDetails.getId());
		memberService.delete(userDetails.getId());
		SecurityContextHolder.clearContext();

		return "redirect:/";
	}
}
