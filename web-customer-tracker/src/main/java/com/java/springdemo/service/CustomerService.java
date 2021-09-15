package com.java.springdemo.service;

import java.util.List;

import com.java.springdemo.entity.Customer;
import com.java.springdemo.entity.License;

public interface CustomerService {
	public List<Customer> getCustomers(int sortField);
	public void saveCustomer(Customer customer);	
	public Customer getCustomer(int customerID);	
	public void deleteCustomer(int customerID);
	public List<Customer> searchCustomers(String searchName);	
	public Customer getCustomerLicenses(int customerID);
	public List<License> getLicenses(int sortField);
	public void deleteLicense(int licenseID);
}