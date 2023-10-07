package moais.todo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginFrontController {

	@GetMapping("/public/front/login")
	public String showLoginPage() {
		return "login";
	}

	@PostMapping("/auth/loginSuccess")
	public String successLogin() {
		return "redirect:/auth/front/tasks";
	}
}
