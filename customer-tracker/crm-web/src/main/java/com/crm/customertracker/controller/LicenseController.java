package com.crm.customertracker.controller;

import java.util.List;

import com.crm.customertracker.entity.security.User;
import com.crm.customertracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.crm.customertracker.entity.customer.License;
import com.crm.customertracker.service.CustomerService;

@Controller
@RequestMapping("/licenses")
public class LicenseController {
	private final CustomerService customerService;
	private final UserService userService;

	public LicenseController(CustomerService customerService, UserService userService) {
		this.customerService = customerService;
		this.userService = userService;
	}

	@ModelAttribute("firstName")
	public String getAuthenticatedUserFirstName() {
		// Obtain the authenticated User from User Service
		User user = userService.retrieveAuthenticatedPrincipalByUsername();

		// Add Authenticated User's First Name to Model Attribute
		return user.getFirstName();
	}

	@GetMapping("/list")
	public String listLicenses(Model model) {
		return findPaginatedLicenses(1, "licenseName", "asc", model);
	}

	@GetMapping("/deleteLicense")
	public String deleteLicense(@RequestParam("licenseId") int licenseId) {
		// Delete an existing License by its ID using Customer Service
		customerService.deleteLicenseById(licenseId);

		return "redirect:/licenses/list";
	}

	@GetMapping("/page/{pageNumber}")
	public String findPaginatedLicenses(@PathVariable(value = "pageNumber") int pageNumber,
										@RequestParam("sortField") String sortField,
										@RequestParam("sortDirection") String sortDirection,
										Model model) {
		// Set the page size for each page of licenses
		int pageSize = 5;

		// Get all licenses from page using customer service
		Page<License> licensePage = customerService.findPaginatedLicenses(pageNumber, pageSize, sortField, sortDirection);
		List<License> licenses = licensePage.getContent();

		// Set Pagination Values to Model Attribute
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalPages", licensePage.getTotalPages());
		model.addAttribute("totalItems", licensePage.getTotalElements());

		// Set Sort Values to Model Attribute
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDirection", sortDirection);
		model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

		// Add all Licenses to Model Attribute
		model.addAttribute("licenses", licenses);

		return "customers/list-licenses";
	}
}