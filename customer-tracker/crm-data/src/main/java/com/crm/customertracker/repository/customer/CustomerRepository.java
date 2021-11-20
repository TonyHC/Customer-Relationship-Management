package com.crm.customertracker.repository.customer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crm.customertracker.entity.customer.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	// JPA performs a Query based on particular characteristics (naming convention of the method):
	// SELECT c FROM Customer c ORDER BY c.lastName
	List<Customer> findAllByOrderByLastNameAsc();
	
	// Use @Query to create Custom Query to get a Customer along with its Licenses by ID (Primary Key)
	@Query("SELECT c FROM Customer c JOIN FETCH c.licenses WHERE c.id = ?1")
	Customer findCustomerLicenses(int customerId);
	
	// Use @Query to create Custom Query to get all Customers whose first or last name sorted by last name
	// matches the name entered by the user
	@Query("SELECT c FROM Customer c WHERE LOWER(c.firstName) LIKE %?1% OR LOWER(c.lastName) LIKE %?1% ORDER BY c.lastName")
	List<Customer> searchEmployeeByFirstOrLastName(String customerName);
}