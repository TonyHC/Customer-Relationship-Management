package com.java.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.springdemo.dao.CustomerDAO;
import com.java.springdemo.dao.LicenseDAO;
import com.java.springdemo.entity.Customer;
import com.java.springdemo.entity.License;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	// Need to Inject the Customer DAO
	// Spring will scan for a Component that implements CustomerDAO interface
	// If more than one implementation, use the @Qualifier to specify the correct one
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private LicenseDAO licenseDAO;
	
	@RequestMapping("/list")
	public String listCustomers(Model model) {
		// Get the List of Customers from Customer DAO
		List<Customer> customers = customerDAO.getCustomers();
		
		// Add Customers to the Spring MVC Model
		model.addAttribute("customers", customers);
		
		// Return the JSP Page Mapping
		return "list-customers";
	}
	
	@RequestMapping("/licenses")
	public String listLicenses(Model model) {
		List<License> licenses = licenseDAO.getLicenses();
		
		model.addAttribute("licenses", licenses);
		
		return "list-licenses";
	}
	
	@RequestMapping("/license")
	public String listCustomerLicenses(Model model) {
		Customer customer = customerDAO.getCustomerLicense();
		
		model.addAttribute("customer", customer);
				
		List<License> licenses = customer.getLicenses();

		model.addAttribute("licenses", licenses);
		
		return "list-customer-licenses";
	}
}