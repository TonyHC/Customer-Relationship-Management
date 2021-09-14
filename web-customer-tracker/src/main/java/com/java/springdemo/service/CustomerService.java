package com.java.springdemo.service;

import java.util.List;

import com.java.springdemo.entity.Customer;
import com.java.springdemo.entity.License;

public interface CustomerService {
	public List<Customer> getCustomers();
	public void saveCustomer(Customer customer);	
	public Customer getCustomer(int customerID);	
	public void deleteCustomer(int customerID);
	public Customer getCustomerLicenses();
	public List<License> getLicenses();
}