package com.crm.customertracker.service;

import com.crm.customertracker.entity.customer.Customer;
import com.crm.customertracker.repository.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CustomerServiceIT {
    @Autowired
    CustomerRepository customerRepository;

    @Test
    void findAllCustomersTest() {
        int customerId = 1;

        Optional<Customer> customer = customerRepository.findById(customerId);

        assertEquals(customerId, customer.get().getId());
    }
}