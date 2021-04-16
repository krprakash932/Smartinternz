package com.prakashcodes.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {
	@Autowired
	UserRepository userRepo;
	@GetMapping("/")
	public String home() {
		return "home";
	}
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	@PostMapping("/process_register")
	public String processing(User user) {
		userRepo.save(user);
		return "dashboard";
	}

}
