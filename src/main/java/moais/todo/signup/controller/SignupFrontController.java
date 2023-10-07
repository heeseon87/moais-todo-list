package moais.todo.signup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignupFrontController {

	@GetMapping("/public/front/signup")
	public String showSignupPage() {
		return "signup";
	}
}
