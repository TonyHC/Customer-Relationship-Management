package com.crm.customertracker.controller;

import com.crm.customertracker.entity.security.User;
import com.crm.customertracker.service.UserService;
import com.crm.customertracker.utils.PasswordHider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordHider passwordHider;

    @GetMapping("/page")
    public String getEmployeeHomePage() {
        return "employees/employee-homepage";
    }

    @GetMapping("/profile")
    public String getEmployeeProfile(Model model) {
        // Obtain the authenticated User from User Service
        User user = userService.retrieveAuthenticatedPrincipalByUsername();

        // Add Authenticated User properties to Model Attribute
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("employeeId", user.getId());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("maskedPassword", passwordHider.passwordMasking(user.getPassword()));

        return "employees/employee-profile";
    }

    @GetMapping("/logout")
    public String getLogoutNavBar(Model model) {
        // Obtain the authenticated User from User Service
        User user = userService.retrieveAuthenticatedPrincipalByUsername();

        // Add Authenticated User's First Name to Model Attribute
        model.addAttribute("firstName", user.getFirstName());

        return "fragments/nav-logout";
    }
}