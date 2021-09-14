package com.java.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/showFormToAddCustomer")
	public String showFormToAddCustomer(Model model) {
		// Create empty Customer Object
		Customer customer = new Customer();
		
		// Add Customer Object to Model - used to bind Form Data
		model.addAttribute("customer", customer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer) {	
		// Save the Customer into DB Using Customer Service 
		customerService.saveCustomer(customer);
		
		// Redirect to "customer/list" Mapping
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdatingCustomer")
	public String showFormToUpdateCustomer(@RequestParam("customerID") int customerID, Model model) {
		// Get the Customer from Customer Service
		Customer customer = customerService.getCustomer(customerID);
		
		// Set Customer as Model Attribute to PrePopulate the Form
		model.addAttribute("customer", customer);
		
		// Send over to the Form
		return "customer-form";
	}
	
	@GetMapping("/deleteCustomer")
	public String deleteCustomer(@RequestParam("customerID") int customerID, Model model) {
		// Delete the Customer from Customer Service
		customerService.deleteCustomer(customerID);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/searchCustomer")
	public String searchCustomer(@RequestParam("searchName") String searchName, Model model) {
		// Search Customers from Custom Service
		List<Customer> customers = customerService.searchCustomers(searchName);
		
		model.addAttribute("customers", customers);
		
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