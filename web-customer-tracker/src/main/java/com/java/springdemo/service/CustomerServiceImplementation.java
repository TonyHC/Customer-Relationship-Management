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
	public List<Customer> getCustomers(int sortField) {
		// Delegate Call to CustomerDAO
		return customerDAO.getCustomers(sortField);
	}
	
	@Override
	@Transactional
	public void saveCustomer(Customer customer) {
		// Delegate Call to CustomerDAO
		customerDAO.saveCustomer(customer);
	}
	
	@Override
	@Transactional
	public Customer getCustomer(int customerID) {
		// Delegate Call to CustomerDAO
		return customerDAO.getCustomer(customerID);
	}
	
	@Override
	@Transactional
	public void deleteCustomer(int customerID) {
		// Delegate Call to CustomerDAO
		customerDAO.deleteCustomer(customerID);
	}	
	
	@Override
	@Transactional
	public List<Customer> searchCustomers(String searchName) {
		// Delegte Call to CustomerDAO
		return customerDAO.searchCustomers(searchName);
	}	
	
	@Override
	@Transactional
	public Customer getCustomerLicenses(int customerID) {
		// Delegate Call to CustomerDAO
		return customerDAO.getCustomerLicense(customerID);
	}

	@Override
	@Transactional
	public List<License> getLicenses(int sortField) {
		// Delegate Call to LicenseDAO
		return licenseDAO.getLicenses(sortField);
	}

	@Override
	@Transactional
	public void deleteLicense(int licenseID) {
		// Delegate Call to LicenseDAO
		 licenseDAO.deleteLicense(licenseID);
	}
}