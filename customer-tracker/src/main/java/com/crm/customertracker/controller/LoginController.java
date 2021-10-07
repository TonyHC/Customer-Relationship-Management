package com.crm.customertracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {
	@GetMapping("/showLoginPage")
	public String showLoginPage() {
		return "security/login";
	}
	
	@GetMapping("/accessDenied")
	public String accessDenied() {
		return "security/access-denied";
	}
}