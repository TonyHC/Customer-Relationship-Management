package com.crm.customertracker.controller;

import com.crm.customertracker.entity.security.User;
import com.crm.customertracker.service.UserService;
import com.crm.customertracker.utils.PasswordHider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {
    public static final int ID = 1;
    public static final String FIRST_NAME = "TJ";
    public static final String LAST_NAME = "Hawk";
    public static final String PASSWORD = "test";

    @Mock
    UserService userService;

    @Mock
    PasswordHider passwordHider;

    @InjectMocks
    EmployeeController employeeController;

    MockMvc mockMvc;
    User user;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();

        user = User.builder().id(ID).firstName(FIRST_NAME)
                .lastName(LAST_NAME).password(PASSWORD).build();

        given(userService.retrieveAuthenticatedPrincipalByUsername()).willReturn(user);
    }

    @DisplayName("Retrieve authenticated user's first name")
    @Test
    void getAuthenticatedUserFirstName() {
        String userFirstName = employeeController.getAuthenticatedUserFirstName();

        assertThat(userFirstName).isEqualTo(FIRST_NAME);
    }

    @DisplayName("Employee home page")
    @Test
    void getEmployeeHomePage() throws Exception {
        mockMvc.perform(get("/employees/page"))
                .andExpect(status().isOk())
                .andExpect(view().name("employees/employee-homepage"));
    }

    @DisplayName("Display employee profile")
    @Test
    void getEmployeeProfile() throws Exception {
        given(passwordHider.passwordMasking(anyString())).willReturn(PASSWORD);

        mockMvc.perform(get("/employees/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("employees/employee-profile"))
                .andExpect(model().attributeExists("user", "maskedPassword", "firstName"));
    }

    @DisplayName("Display logout navigation bar")
    @Test
    void getLogoutNavBar() throws Exception {
        mockMvc.perform(get("/employees/logout"))
                .andExpect(status().isOk())
                .andExpect(view().name("fragments/nav-logout"));
    }
}