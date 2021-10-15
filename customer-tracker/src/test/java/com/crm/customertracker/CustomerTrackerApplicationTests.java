package com.crm.customertracker;

import com.crm.customertracker.entity.customer.Customer;
import com.crm.customertracker.entity.security.User;
import com.crm.customertracker.service.CustomerService;
import com.crm.customertracker.service.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CustomerTrackerApplicationTests {
	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserService userService;

	@Nested
	class CustomerTests {
		@Test
		void Should_ReturnAllCustomers_When_CallFindAllCustomers() {
			// Given
			int numOfCustomers = 5;

			// When
			List<Customer> customers = customerService.findAllCustomers();

			// Then
			assertThat(customers.size(), is(numOfCustomers));
		}

		@Test
		void Should_UpdateCustomerLastName_When_CallSaveCustomer() {
			// Given
			Customer customer = customerService.findCustomerById(3);
			customer.setLastName("Jones");

			// When
			customerService.saveCustomer(customer);

			// Then
			assertAll(
					() -> assertEquals("Jones", customer.getLastName()),
					() -> assertEquals(3, customer.getId())
			);
		}

		@Test
		void Should_ReturnListOfCustomers_When_CallFindCustomersByName() {
			// Given
			int expectedCustomers = 2;

			// When
			List<Customer> customers = customerService.findCustomersByName("Ma");

			// Then
			assertThat(customers.size(), is(expectedCustomers));
		}

		@Test
		void Should_ThrowRuntimeException_When_CustomerIdDoesNotExist() {
			// Given
			int customerId = 62;

			// When
			Executable executable = () -> customerService.findCustomerById(customerId);

			// Then
			assertThrows(RuntimeException.class, executable);
		}
	}

	@Nested
	class UserTests {
		@Test
		void Should_ReturnUser_When_CallFindByUserName() {
			// Given
			String authenticatedName = "John";

			// When
			User user = userService.findByUserName("john");

			// Then
			assertEquals(user.getFirstName(), authenticatedName);
		}
	}
}