package com.java.springdemo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.springdemo.service.UserService;
import com.java.springdemo.user.RegisterUser;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	@Autowired
	private UserService userService;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	private Map<String, String> roles;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		// Every time a String is encountered, the String's leading and trailing white spaces are trimmed
		// If only white spaces, set String to null
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}	
	
	@PostConstruct
	protected void loadRoles() {
		roles = new HashMap<String, String>();
		
		// key = user role, value = display to user
		roles.put("ROLE_EMPLOYEE", "Employee");
		roles.put("ROLE_MANAGER", "Manager");
		roles.put("ROLE_ADMIN", "Admin");
	}
	
	@GetMapping("/showRegistrationForm")
	public String showMyLoginPage(Model theModel) {
		theModel.addAttribute("registerUser", new RegisterUser());
		theModel.addAttribute("roles", roles);
		
		return "registration-form";
	}

	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(@Valid @ModelAttribute("registerUser") RegisterUser aRegisterUser, 
				BindingResult theBindingResult, Model theModel) {
						
		String userName = aRegisterUser.getUserName();
		
		logger.info("Processing registration form for: " + userName);
		logger.info("BindingResult Errors: " + theBindingResult.getAllErrors());
		// Registration Form Validation to check if any Constraint Validators were violated
		if (theBindingResult.hasErrors()) {
			theModel.addAttribute("roles", roles);

			return "registration-form";	
		}
		
		// Check if Username already exists in DB
		if (userService.findByUserName(userName) != null) {
			theModel.addAttribute("registerUser", new RegisterUser());
			theModel.addAttribute("roles", roles);
			
			theModel.addAttribute("registrationError", "User name already exists.");
			logger.warning("User name already exists.");
			
			return "registration-form";			
		}
		
		// Give newly created user's with the default role of "employee"
		List<String> roles = new ArrayList<>();
        // Stores a String representation of an authority granted to the Authentication object.
		roles.add("ROLE_EMPLOYEE");
        
        // If the User selects the role other than EMPLOYEE,
        // then also add that role (multiple roles for one user)
        String formRole = aRegisterUser.getFormRole();
        if (!formRole.equals("ROLE_EMPLOYEE")) {
        	roles.add(formRole);
        }
        
        userService.saveUser(aRegisterUser, roles);
		
        logger.info("Successfully created user: " + userName);
        
        return "registration-confirmation";		
	}
}
