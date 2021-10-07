package com.crm.customertracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crm.customertracker.entity.customer.License;
import com.crm.customertracker.service.CustomerService;

@Controller
@RequestMapping("/licenses")
public class LicenseController {
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listLicenses(Model model) {
		// Find all Licenses using Customer Service
		List<License> licenses = customerService.findAllLicenses();
		
		// Add all Licenses to Model Attribute
		model.addAttribute("licenses", licenses);
		
		return "customers/list-licenses";
	}
	
	@GetMapping("/deleteLicense")
	public String deleteLicense(@RequestParam("licenseId") int licenseId, Model model) {
		// Delete an existing License by its ID using Customer Service
		customerService.deleteLicense(licenseId);
		
		return "redirect:/licenses/list";
	}
}