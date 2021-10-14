package com.crm.customertracker;

import com.crm.customertracker.entity.customer.Customer;
import com.crm.customertracker.service.CustomerServiceImplementation;
import com.crm.customertracker.service.UserServiceImplementation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CustomerTrackerApplicationTests {
    @Autowired
    private CustomerServiceImplementation customerServiceImplementation;

    @Nested
    class CustomerTests {
        @Test
        void Should_ReturnAllCustomers_When_CallFindAllCustomers() {
            // Given
            int numOfCustomers = 5;

            // When
            List<Customer> customers = customerServiceImplementation.findAllCustomers();

            // Then
            assertThat(customers.size(), is(numOfCustomers));
        }

        @Test
        void Should_UpdateCustomerLastName_WhenCallSaveCustomer() {
            // Given
            Customer customer = customerServiceImplementation.findCustomerById(3);
            customer.setLastName("Jones");

            // When
            customerServiceImplementation.saveCustomer(customer);

            // Then
            assertAll(
                    () -> assertEquals("Jones", customer.getLastName()),
                    () -> assertEquals(3, customer.getId())
            );
        }
    }
}