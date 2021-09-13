package com.java.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.java.springdemo.entity.Customer;
import com.java.springdemo.entity.License;
import com.java.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {	
	// Need to Inject the CustomerService
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomers(Model model) {
		// Get the List of Customers from Customer Service
		List<Customer> customers = customerService.getCustomers();
		
		// Add Customers to the Spring MVC Model
		model.addAttribute("customers", customers);
		
		// Return the JSP Page Mapping
		return "list-customers";
	}
	
	@RequestMapping(path = "/licenses", method = RequestMethod.GET)
	public String listLicenses(Model model) {
		List<License> licenses = customerService.getLicenses();
		
		model.addAttribute("licenses", licenses);
		
		return "list-licenses";
	}
	
	@RequestMapping("/license")
	public String listCustomerLicenses(Model model) {
		Customer customer = customerService.getCustomerLicenses();
		
		model.addAttribute("customer", customer);
				
		List<License> licenses = customer.getLicenses();

		model.addAttribute("licenses", licenses);
		
		return "list-customer-licenses";
	}
}