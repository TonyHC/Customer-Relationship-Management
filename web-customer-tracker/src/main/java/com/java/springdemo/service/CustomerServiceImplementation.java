package com.java.springdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional("customerTransactionManager")
	public List<Customer> getCustomers(int sortField) {
		// Delegate Call to CustomerDAO
		return customerDAO.getCustomers(sortField);
	}
	
	@Override
	@Transactional("customerTransactionManager")
	public void saveCustomer(Customer customer) {
		// Delegate Call to CustomerDAO
		customerDAO.saveCustomer(customer);
	}
	
	@Override
	@Transactional("customerTransactionManager")
	public Customer getCustomer(int customerID) {
		// Delegate Call to CustomerDAO
		return customerDAO.getCustomer(customerID);
	}
	
	@Override
	@Transactional("customerTransactionManager")
	public void deleteCustomer(int customerID) {
		// Delegate Call to CustomerDAO
		customerDAO.deleteCustomer(customerID);
	}	
	
	@Override
	@Transactional("customerTransactionManager")
	public List<Customer> searchCustomers(String searchName) {
		// Delegte Call to CustomerDAO
		return customerDAO.searchCustomers(searchName);
	}	
	
	@Override
	@Transactional("customerTransactionManager")
	public Customer getCustomerLicenses(int customerID) {
		// Delegate Call to CustomerDAO
		return customerDAO.getCustomerLicense(customerID);
	}

	@Override
	@Transactional("customerTransactionManager")
	public List<License> getLicenses(int sortField) {
		// Delegate Call to LicenseDAO
		return licenseDAO.getLicenses(sortField);
	}

	@Override
	@Transactional("customerTransactionManager")
	public void deleteLicense(int licenseID) {
		// Delegate Call to LicenseDAO
		 licenseDAO.deleteLicense(licenseID);
	}
}