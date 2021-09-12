package com.java.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.springdemo.dao.CustomerDAO;
import com.java.springdemo.entity.Customer;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	// Need to Inject the Customer DAO
	// Spring will scan for a Component that implements CustomerDAO interface
	// If more than one implementation, use the @Qualifier to specify the correct one
	@Autowired
	private CustomerDAO customerDAO;
	
	@RequestMapping("/list")
	public String listCustomers(Model model) {
		// Get the List of Customers from Customer DAO
		List<Customer> customers = customerDAO.getCustomers();
		
		// Add Customers to the Spring MVC Model
		model.addAttribute("customers", customers);
		
		// Return the JSP Page Mapping
		return "list-customers";
	}
}