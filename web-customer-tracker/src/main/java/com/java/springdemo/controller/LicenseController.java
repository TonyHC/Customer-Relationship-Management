package com.java.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.java.springdemo.entity.License;
import com.java.springdemo.service.CustomerService;
import com.java.springdemo.utils.SortUtils;

@Controller
@RequestMapping("/license")
public class LicenseController {
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
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
	
	@GetMapping("/deleteLicense")
	public String deleteLicense(@RequestParam("licenseID") int licenseID) {
		// Delete the License from Customer Service using License's id
		customerService.deleteLicense(licenseID);
		
		return "redirect:/license/list";
	}
}