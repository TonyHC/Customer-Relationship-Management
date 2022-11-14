package com.crm.customertracker.controller;

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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LicenseControllerTest {
    public static final int PAGE_NUMBER = 1;
    public static final int PAGE_SIZE = 5;
    public static final String SORT_FIELD = "licenseName";
    public static final String SORT_DIRECTION = "asc";
    @Mock
    private CustomerService customerService;

    @Mock
    private UserService userService;

    @InjectMocks
    private LicenseController licenseController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(licenseController).build();

        given(userService.retrieveAuthenticatedPrincipalByUsername()).willReturn(User.builder().firstName("Tom").build());
    }

    @DisplayName("Display a list of licenses")
    @Test
    void listLicenses() throws Exception {
        given(customerService.findPaginatedLicenses(PAGE_NUMBER, PAGE_SIZE, SORT_FIELD, SORT_DIRECTION))
                .willReturn(Page.empty());

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

    @DisplayName("Display page(s) containing a list of licenses")
    @Test
    void findPaginatedLicenses() throws Exception {
        given(customerService.findPaginatedLicenses(PAGE_NUMBER, PAGE_SIZE, SORT_FIELD, SORT_DIRECTION))
                .willReturn(Page.empty());

        mockMvc.perform(get("/licenses/page/1")
                        .param("sortField", SORT_FIELD)
                        .param("sortDirection", SORT_DIRECTION))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/list-licenses"))
                .andExpect(model().attributeExists("currentPage", "totalPages", "totalItems",
                        "sortField", "sortDirection", "reverseSortDirection", "licenses"));
    }
}