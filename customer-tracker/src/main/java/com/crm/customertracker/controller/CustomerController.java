package com.crm.customertracker.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crm.customertracker.entity.customer.Customer;
import com.crm.customertracker.entity.customer.License;
import com.crm.customertracker.service.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomers(Model model) {
		/*
			// Get all Customers from Customer Service
			List<Customer> customers = customerService.findAllCustomers();
			
			// Add Customers to Model Attribute
			model.addAttribute("customers", customers);
			
			return "customers/list-customers";
		*/
		
		// Call the findPaginated(): Set the starting page number (zero-based), sort field,
		// sort direction, and model object
		return findPaginated(1, "firstName", "asc", model);
	}
	
	@GetMapping("/showFormForAddingCustomer")
	public String showFormForAddingCustomer(Model model) {
		// Create a empty Customer object
		Customer customer = new Customer();
		
		// Add empty Customer object to Model Attribute 
		model.addAttribute("customer", customer);
		
		return "customers/customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {
		// If the 'customer-form' has any Form Errors, then return back to the 'customer-form'
		if (bindingResult.hasErrors()) {
			return "customers/customer-form";
		} else {
			// Else either save or update the Customer depending on whether the Customer has an id (Primary Key)
			customerService.saveCustomer(customer);
			
			// Once the Customer has been saved, redirect back to 'customers-lists'
			return "redirect:/customers/list";
		}
	}
	
	@GetMapping("/showFormForUpdatingCustomer")
	public String showFormForUpdatingCustomer(@RequestParam("customerId") int customerId, Model model) {
		// Find a Customer by its ID using Customer Service
		Customer customer = customerService.findCustomerById(customerId);
		
		// Add the existing Customer to the Model Attribute to populate the Form
		model.addAttribute("customer", customer);
		
		return "customers/customer-form";
	}
	
	@GetMapping("/deleteCustomer") 
	public String deleteCustomer(@RequestParam("customerId") int customerId) {
		// Delete a existing Customer by its ID using Customer Service
		customerService.deleteCustomer(customerId);
		
		return "redirect:/customers/list";
	}
	
	@GetMapping("/licenses")
	public String listCustomerLicenses(@RequestParam("customerId") int customerId, Model model) {
		Customer customer = customerService.findCustomerLicenses(customerId);
		
		model.addAttribute("customer", customer);
		
		List<License> licenses = customer.getLicenses();
		
		model.addAttribute("licenses", licenses);
		
		return "customers/customer-licenses";
	}
	
	@GetMapping("/searchCustomers")
	public String searchCustomersByName(@RequestParam("customerName") String customerName, Model model) {
		// Find a List of Customers whose name matches the name being searched using Customer Service
		List<Customer> customers = customerService.findCustomersByName(customerName);
		
		// Add the matching Customers to the Model Attribute
		model.addAttribute("customers", customers);
		
		return "customers/list-customers";
	}
	
	@GetMapping("/page/{pageNumber}")
	public String findPaginated(@PathVariable(value = "pageNumber") int pageNumber,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDirection") String sortDirection,
			Model model) {
		// Set Page Size for each Page
		int pageSize = 5;
		
		// Get all Customers from Page using CustomerService
		Page<Customer> page = customerService.findPaginated(pageNumber, pageSize, sortField, sortDirection);
		List<Customer> customers = page.getContent();
		
		// Set Pagination Values to Model Attribute
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		// Set Sort Values to Model Attribute
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDirection", sortDirection);
		model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
		
		// Add List of Customers to Model Attribute
		model.addAttribute("customers", customers);
		
		return "customers/list-customers";
	}
}