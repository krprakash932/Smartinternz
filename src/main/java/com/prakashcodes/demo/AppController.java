package com.prakashcodes.demo;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {
	@Autowired
	UserRepository userRepo;
	@Autowired
	JobsRepository jobRepo;
	@Autowired
	private JavaMailSender sender;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/process_register")
	public String processing(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(user.getPassword());
	    user.setPassword(encodedPassword);
	    userRepo.save(user);
	    try {
	    	sendEmail(user.getEmail());
	    }
	    catch(Exception e) {
	    	System.out.println(e);
	    }
		return "/dashboard";
	}
	
	@GetMapping("/dashboard")
	public String listUsers(Model model) {
		return "dashboard";
	}
	
	@GetMapping("/apply")
	public String showForm(Model model) {
		model.addAttribute("jobs", new jobs());
		return "apply";
	}
		
	@PostMapping("/process_apply")
	public String processApply(jobs jobs) {
		jobRepo.save(jobs);
		System.out.println(jobs.getId());
		System.out.println(jobs.getName());
		return "display";
	}
	@GetMapping("/display")
	public String displayAppliedJobs(Model model) {
		model.addAttribute("jobs", jobRepo.findAll());
		return "display";
	}
	private void sendEmail(String email) throws Exception{
		MimeMessage msg=sender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(msg);
		helper.setTo(email);
		helper.setText("Welcome");
		helper.setSubject("Thanks for Registering");
		
		sender.send(msg);
				
	}

}
