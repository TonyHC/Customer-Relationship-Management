package com.java.springdemo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.springdemo.entity.Customer;
import com.java.springdemo.entity.License;
import com.java.springdemo.service.CustomerService;
import com.java.springdemo.utils.SortUtils;

@Controller
@RequestMapping("/customer")
public class CustomerController {	
	// Need to Inject the CustomerService
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomers(Model model, @RequestParam(required = false) String sort) {
		List<Customer> customers = null;
		
		// If sort field provided, use the sort provided and get the List of Customers from Customer Service
		if (sort != null) {
			int sortField = Integer.parseInt(sort);
			customers = customerService.getCustomers(sortField);
		} else {
			// If no sort field provided, default to sorting by Customer Last Name
			customers = customerService.getCustomers(SortUtils.LAST_NAME);
		}
		
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
	public String saveCustomer(@Valid @ModelAttribute("customer") Customer customer, BindingResult bindingResult) {	
		// If Form Data for Customer has violates some constraint, then return back to customer-form.jsp and display error message
		if (bindingResult.hasErrors()) {
			return "customer-form";
		} else {
			// Save the Customer into DB Using Customer Service 
			customerService.saveCustomer(customer);
			
			// Redirect to "customer/list" Mapping
			return "redirect:/customer/list";
		}
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
	public String deleteCustomer(@RequestParam("customerID") int customerID) {
		// Delete the Customer from Customer Service using Customer's id
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
	
	@GetMapping("/licenses")
	public String listLicenses(Model model, @RequestParam(required = false) String sort) {
		List<License> licenses = null;
		
		// If sort field provided, use the sort provided and get the List of Licenses from Customer Service
		if (sort != null) {
			int sortField = Integer.parseInt(sort);
			licenses = customerService.getLicenses(sortField);
		} else {
			// If no sort field provided, default to sorting by Licenses Expiration Date
			licenses = customerService.getLicenses(SortUtils.EXPIRATION_DATE);
		}
		
		// Add Licenses to the Spring MVC Model
		model.addAttribute("licenses", licenses);
		
		// Return to list-licenses.jsp
		return "list-licenses";
	}
	
	@GetMapping("/license")
	public String listCustomerLicenses(@RequestParam("customerID") int customerID, Model model) {
		// Get the desired Customer along with its Licenses using the Customer's id from Customer Service
		Customer customer = customerService.getCustomerLicenses(customerID);
		
		// Add desired Customer to Spring MVC Model
		model.addAttribute("customer", customer);
		
		// Retrieve the Licenses from Customer
		List<License> licenses = customer.getLicenses();

		// Add those Licenses to Spring MVC Model
		model.addAttribute("licenses", licenses);
		
		// Return to customer-licenses.jsp
		return "customer-licenses";
	}
	
	@GetMapping("/deleteLicense")
	public String deleteLicense(@RequestParam("licenseID") int licenseID) {
		// Delete the License from Customer Service using License's id
		customerService.deleteLicense(licenseID);
		
		return "redirect:/customer/licenses";
	}
}