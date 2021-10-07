package com.crm.customertracker.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.crm.customertracker.entity.customer.Customer;
import com.crm.customertracker.entity.customer.License;

public interface CustomerService {
	List<Customer> findAllCustomers();
	Customer findCustomerById(int customerId);
	void saveCustomer(Customer customer);
	void deleteCustomer(int customerId);
	Customer findCustomerLicenses(int customerId);
	List<Customer> findCustomersByName(String customerName);
	Page<Customer> findPaginated(int pageNumber, int pageSize, String sortField, String sortDirection);
	
	List<License> findAllLicenses();
	void deleteLicense(int licenseId);
}