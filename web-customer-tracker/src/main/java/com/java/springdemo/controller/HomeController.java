package com.java.springdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/employees")
	public String showEmployees() {
		return "employees";
	}
}