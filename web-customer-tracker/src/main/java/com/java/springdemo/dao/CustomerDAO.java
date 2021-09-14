package com.java.springdemo.dao;

import java.util.List;

import com.java.springdemo.entity.Customer;

public interface CustomerDAO {
	public Customer getCustomerLicense();
	public void saveCustomer(Customer customer);	
	public Customer getCustomer(int customerID);
	public void deleteCustomer(int customerID);	
	public List<Customer> searchCustomers(String searchName);	
	public List<Customer> getCustomers();
}