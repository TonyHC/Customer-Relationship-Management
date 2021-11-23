package com.crm.customertracker.service;

import com.crm.customertracker.entity.customer.Customer;
import com.crm.customertracker.entity.customer.License;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
	List<Customer> findAllCustomers();
	Customer findCustomerById(int customerId);
	void saveCustomer(Customer customer);
	void deleteCustomerById(int customerId);
	Customer findCustomerLicenses(int customerId);
	List<Customer> findCustomersByName(String customerName);
	Page<Customer> findPaginatedCustomers(int pageNumber, int pageSize, String sortField, String sortDirection);
	
	List<License> findAllLicenses();
	void deleteLicenseById(int licenseId);
	Page<License> findPaginatedLicenses(int pageNumber, int pageSize, String sortField, String sortDirection);
}