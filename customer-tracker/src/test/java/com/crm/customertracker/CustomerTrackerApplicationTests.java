package com.crm.customertracker;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.crm.customertracker.entity.customer.Customer;
import com.crm.customertracker.repository.customer.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class CustomerTrackerApplicationTests {
	@Autowired
	private CustomerRepository customerRepository;

	private Customer customer;

	@Before
	public void initializeDataObjects() {
		customer = new Customer();
		
		customer.setFirstName("Tom");
		customer.setLastName("Marks");
		customer.setEmail("TM@mail.com");
	}
	
	@Test
	public void shouldSaveCustomerToDB() {
		Customer saveCustomer = customerRepository.save(customer);
		Optional<Customer> customerFromDB = customerRepository.findById(saveCustomer.getId());
		assertTrue(customerFromDB.isPresent());
	}
}
