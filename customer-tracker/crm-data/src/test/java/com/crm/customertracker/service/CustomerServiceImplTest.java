package com.crm.customertracker.service;

import com.crm.customertracker.entity.customer.Customer;
import com.crm.customertracker.entity.customer.License;
import com.crm.customertracker.repository.customer.CustomerRepository;
import com.crm.customertracker.repository.customer.LicenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    public static final int ID = 1;
    public static final String FIRST_NAME = "TJ";
    public static final String LAST_NAME = "Hawk";
    public static final String EMAIL = "TJH@gmail.com";

    @Mock
    CustomerRepository customerRepository;

    @Mock
    LicenseRepository licenseRepository;

    @InjectMocks
    CustomerServiceImpl customerServiceImpl;

    Customer customer;
    License license;

    @BeforeEach
    void setUp() {
        customer = Customer.builder().id(ID).firstName(FIRST_NAME)
                .lastName(LAST_NAME).email(EMAIL).build();

        license = License.builder().id(ID).licenseName("Photoshop").customer(customer)
                .startDate(Date.from(Instant.now()))
                .expirationDate(new Date(System.currentTimeMillis())).build();

        customer.setLicenses(List.of(license));
    }

    @DisplayName("Display a list of customers")
    @Test
    void findAllCustomers() {
        List<Customer> customers = Arrays.asList(customer, customer);

        given(customerRepository.findAllByOrderByLastNameAsc()).willReturn(customers);

        List<Customer> returnedCustomers = customerServiceImpl.findAllCustomers();

        assertThat(returnedCustomers.size()).isEqualTo(2);
    }

    @DisplayName("Successfully found a customer by id")
    @Test
    void findCustomerByIdFound() {
        given(customerRepository.findById(anyInt())).willReturn(Optional.of(customer));

        Customer returnedCustomer = customerServiceImpl.findCustomerById(anyInt());

        assertAll(
                () -> assertThat(returnedCustomer).isNotNull(),
                () -> assertThat(returnedCustomer.getId()).isEqualTo(ID),
                () -> assertThat(returnedCustomer.getFirstName()).isEqualTo(FIRST_NAME)
        );
    }

    @DisplayName("Unable to find a customer by id")
    @Test
    void findCustomerByIdNotFound() {
        given(customerRepository.findById(anyInt())).willThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> customerServiceImpl.findCustomerById(anyInt()));
    }

    @DisplayName("Save a customer")
    @Test
    void saveCustomer() {
        customerServiceImpl.saveCustomer(any());

        then(customerRepository).should().save(any());
    }

    @DisplayName("Delete customer by id")
    @Test
    void deleteCustomerById() {
        customerServiceImpl.deleteCustomerById(anyInt());

        then(customerRepository).should().deleteById(anyInt());
    }

    @DisplayName("Find customer along with its license(s) by id")
    @Test
    void findCustomerLicenses() {
        given(customerRepository.findCustomerLicenses(anyInt())).willReturn(customer);

        Customer foundCustomer = customerServiceImpl.findCustomerLicenses(anyInt());

        assertThat(foundCustomer).isNotNull();
        assertThat(foundCustomer.getLicenses()).isNotNull();
    }

    @DisplayName("Unable to find customer by first or last name")
    @Test
    void findCustomersByNameFound() {
        given(customerRepository.searchEmployeeByFirstOrLastName(FIRST_NAME)).willReturn(List.of(customer));

        List<Customer> customerList = customerServiceImpl.findCustomersByName(FIRST_NAME);

        assertAll(
                () -> assertThat(customerList.size()).isEqualTo(1),
                () -> assertThat(customerList.get(0).getEmail()).isEqualTo(EMAIL),
                () -> assertThat(customerList.get(0).getLicenses()).isNotNull()
        );
    }

    @DisplayName("Found customer by first or last name")
    @Test
    void findCustomersByNameEmptyString() {
        given(customerRepository.findAllByOrderByLastNameAsc()).willReturn(Arrays.asList(customer, customer));

        List<Customer> customerList = customerServiceImpl.findCustomersByName("");

        assertThat(customerList.size()).isEqualTo(2);
    }

    @DisplayName("Display a page containing a list of customers")
    @Test
    void findPaginated() {
        Page<Customer> customerPage = Page.empty();

        given(customerRepository.findAll(any(Pageable.class))).willReturn(customerPage);

        Page<Customer> returnCustomerPage =
                customerServiceImpl.findPaginated(1, 5, "firstName", "asc");

        assertThat(returnCustomerPage).isNotNull();
    }

    @DisplayName("Display a list of licenses")
    @Test
    void findAllLicenses() {
        given(licenseRepository.findAll()).willReturn(Arrays.asList(license, license));

        List<License> foundLicenses = customerServiceImpl.findAllLicenses();

        assertThat(foundLicenses.size()).isEqualTo(2);
    }

    @DisplayName("Delete a license by id")
    @Test
    void deleteLicenseById() {
        customerServiceImpl.deleteLicenseById(anyInt());

        then(licenseRepository).should().deleteById(anyInt());
    }
}