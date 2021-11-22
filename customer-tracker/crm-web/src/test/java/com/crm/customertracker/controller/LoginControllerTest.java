package com.crm.customertracker.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
    @InjectMocks
    LoginController loginController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @DisplayName("Login page")
    @Test
    void showLoginPage() throws Exception {
        mockMvc.perform(get("/login/showLoginPage"))
                .andExpect(status().isOk())
                .andExpect(view().name("security/login"));
    }

    @DisplayName("Displays access denied page")
    @Test
    void accessDenied() throws Exception {
        mockMvc.perform(get("/login/accessDenied"))
                .andExpect(status().isOk())
                .andExpect(view().name("security/access-denied"));
    }
}