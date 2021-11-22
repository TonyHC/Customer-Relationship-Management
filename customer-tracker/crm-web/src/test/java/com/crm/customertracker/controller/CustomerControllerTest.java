package com.crm.customertracker.controller;

import com.crm.customertracker.entity.customer.Customer;
import com.crm.customertracker.entity.customer.License;
import com.crm.customertracker.entity.security.User;
import com.crm.customertracker.service.CustomerService;
import com.crm.customertracker.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    public static final String FIRST_NAME = "Tom";
    public static final String SORT_FIELD = "firstName";
    public static final String SORT_DIRECTION = "asc";
    public static final int PAGE_NUMBER = 1;
    public static final int PAGE_SIZE = 5;
    public static final String CUSTOMER_ID = "1";

    @Mock
    CustomerService customerService;

    @Mock
    UserService userService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();

        User user = User.builder().id(PAGE_NUMBER).firstName(FIRST_NAME).build();

        given(userService.retrieveAuthenticatedPrincipalByUsername()).willReturn(user);
    }

    @DisplayName("Retrieve authenticated user's first name")
    @Test
    void getAuthenticatedUserFirstName() {
        String userFirstName = customerController.getAuthenticatedUserFirstName();

        assertThat(userFirstName).isEqualTo(FIRST_NAME);
    }

    @DisplayName("Display a list of customers")
    @Test
    void listCustomers() throws Exception {
        given(customerService.findPaginated(PAGE_NUMBER, PAGE_SIZE, SORT_FIELD, SORT_DIRECTION))
                .willReturn(Page.empty());

        mockMvc.perform(get("/customers/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/list-customers"))
                .andExpect(model().attributeExists("customers"));
    }

    @DisplayName("Display form for adding a new customer")
    @Test
    void showFormForAddingCustomer() throws Exception {
        mockMvc.perform(get("/customers/showFormForAddingCustomer"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/customer-form"))
                .andExpect(model().attributeExists("customer", SORT_FIELD));
    }

    @DisplayName("Save a valid customer")
    @Test
    void saveCustomerValidCustomer() throws Exception {
        mockMvc.perform(post("/customers/saveCustomer")
                        .param("firstName","Roy")
                        .param("lastName", "Hoot")
                        .param("email", "rhoots@gmail.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers/list"));

        then(customerService).should().saveCustomer(any(Customer.class));
    }

    @DisplayName("Display the customer form again for invalid customer")
    @Test
    void saveCustomerInvalidCustomer() throws Exception {
        mockMvc.perform(post("/customers/saveCustomer")
                        .param(SORT_FIELD, "Roy"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/customer-form"));
    }

    @DisplayName("Display form for updating an existing customer")
    @Test
    void showFormForUpdatingCustomer() throws Exception {
        given(customerService.findCustomerById(anyInt())).willReturn(Customer.builder().build());

        mockMvc.perform(get("/customers/showFormForUpdatingCustomer")
                        .param("customerId", CUSTOMER_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/customer-form"))
                .andExpect(model().attributeExists("customer"));
    }

    @DisplayName("Delete customer by id")
    @Test
    void deleteCustomer() throws Exception {
        mockMvc.perform(get("/customers/deleteCustomer")
                        .param("customerId", CUSTOMER_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customers/list"));

        then(customerService).should().deleteCustomerById(anyInt());
    }

    @DisplayName("Display the customer's license(s)")
    @Test
    void listCustomerLicenses() throws Exception {
        given(customerService.findCustomerLicenses(anyInt()))
                .willReturn(Customer.builder().licenses(List.of(License.builder().build())).build());

        mockMvc.perform(get("/customers/licenses")
                        .param("customerId", CUSTOMER_ID))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/customer-licenses"))
                .andExpect(model().attributeExists("licenses", "customer"));
    }

    @DisplayName("Search for customer(s) by either first or last name")
    @Test
    void searchCustomersByName() throws Exception {
        given(customerService.findCustomersByName(anyString())).willReturn(List.of(Customer.builder().build()));

        mockMvc.perform(get("/customers/searchCustomers")
                        .param("customerName", "Steve"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/list-customers"))
                .andExpect(model().attributeExists("customers"));
    }

    @DisplayName("Display page(s) containing a list of customers")
    @Test
    void findPaginated() throws Exception {
        given(customerService.findPaginated(PAGE_NUMBER, PAGE_SIZE, SORT_FIELD, SORT_DIRECTION))
                .willReturn(Page.empty());

        mockMvc.perform(get("/customers/page/1")
                        .param("sortField", SORT_FIELD)
                        .param("sortDirection", SORT_DIRECTION))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/list-customers"))
                .andExpect(model().attributeExists("currentPage", "totalPages", "totalItems",
                        "sortField", "sortDirection", "reverseSortDirection", "customers"));
    }
}