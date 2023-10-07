package moais.todo.signup.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import moais.todo.signup.dto.SignupReq;
import moais.todo.signup.service.SignupService;

@RequiredArgsConstructor
@Controller
public class SignupController {

	private final SignupService signupService;

	@PostMapping("/public/signup")
	public String signup(@Valid SignupReq req) {
		signupService.signup(req);
		return "redirect:/public/front/login";
	}
}
