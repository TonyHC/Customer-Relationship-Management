package com.java.springdemo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.springdemo.dao.CustomerDAO;
import com.java.springdemo.dao.LicenseDAO;
import com.java.springdemo.entity.Customer;
import com.java.springdemo.entity.License;

@Service
public class CustomerServiceImplementation implements CustomerService {
	// Inject the CustomerDAO
	@Autowired
	private CustomerDAO customerDAO;
	
	// Inject the LicenseDAO
	@Autowired
	private LicenseDAO licenseDAO;
	
	@Override
	@Transactional
	public List<Customer> getCustomers() {
		// Delegate Call to CustomerDAO
		return customerDAO.getCustomers();
	}

	@Override
	@Transactional
	public Customer getCustomerLicenses() {
		// Delegate Call to CustomerDAO
		return customerDAO.getCustomerLicense();
	}

	@Override
	@Transactional
	public List<License> getLicenses() {
		// Delegate Call to LicenseDAO
		return licenseDAO.getLicenses();
	}
}