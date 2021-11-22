package com.crm.customertracker.controller;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LicenseControllerTest {
    @Mock
    CustomerService customerService;

    @Mock
    UserService userService;

    @InjectMocks
    LicenseController licenseController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(licenseController).build();
    }

    @DisplayName("Display a list of licenses")
    @Test
    void listLicenses() throws Exception {
        given(customerService.findAllLicenses()).willReturn(List.of(License.builder().build()));
        given(userService.retrieveAuthenticatedPrincipalByUsername()).willReturn(User.builder().firstName("Tom").build());

        mockMvc.perform(get("/licenses/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/list-licenses"))
                .andExpect(model().attributeExists("licenses", "firstName"));
    }

    @DisplayName("Delete a license by id")
    @Test
    void deleteLicense() throws Exception {
        mockMvc.perform(get("/licenses/deleteLicense")
                        .param("licenseId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/licenses/list"));

        then(customerService).should().deleteLicenseById(anyInt());
    }
}