package com.java.springdemo.dao;

import java.util.List;

import com.java.springdemo.entity.Customer;

public interface CustomerDAO {
	public Customer getCustomerLicense();
	public List<Customer> getCustomers();
}