package com.crm.customertracker.controller;

import com.crm.customertracker.entity.security.RegisterUser;
import com.crm.customertracker.entity.security.User;
import com.crm.customertracker.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("classpath:security.properties")
@ExtendWith(MockitoExtension.class)
class RegistrationControllerTest {
    public static final String USERNAME = "ThomasTheSecond";
    public static final String PASSWORD = "admin";
    public static final String FIRST_NAME = "Marcus";
    public static final String LAST_NAME = "Porter";
    public static final String EMAIL = "MP@gmail.com";

    @Mock
    private UserService userService;

    @InjectMocks
    private RegistrationController registrationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(registrationController).build();
    }

    @DisplayName("Display user registration form")
    @Test
    void showRegistrationForm() throws Exception {
        mockMvc.perform(get("/register/showRegistrationForm"))
                .andExpect(status().isOk())
                .andExpect(view().name("security/registration-form"))
                .andExpect(model().attributeExists("registerUser"));
    }

    @DisplayName("Process an invalid user being created")
    @Test
    void processRegistrationFormInvalidRegisteredUser() throws Exception {
        mockMvc.perform(post("/register/processRegistrationForm")
                        .param("userName", USERNAME))
                .andExpect(status().isOk())
                .andExpect(view().name("security/registration-form"));
    }

    @DisplayName("Process an existing registered user")
    @Test
    void processRegistrationFormRegisteredUserExists() throws Exception {
        given(userService.findByUserName(USERNAME)).willReturn(User.builder().username(USERNAME).build());

        mockMvc.perform(post("/register/processRegistrationForm")
                        .param("userName", USERNAME)
                        .param("password", PASSWORD)
                        .param("matchingPassword", PASSWORD)
                        .param("firstName", FIRST_NAME)
                        .param("lastName", LAST_NAME)
                        .param("email", EMAIL))
                .andExpect(status().isOk())
                .andExpect(view().name("security/registration-form"))
                .andExpect(model().attributeExists("registrationError"));
    }

    @DisplayName("Process an valid registered user")
    @Test
    void processRegistrationFormValidRegisteredUser() throws Exception {
        given(userService.findByUserName(USERNAME)).willReturn(null);

        mockMvc.perform(post("/register/processRegistrationForm")
                        .param("userName", USERNAME)
                        .param("password", PASSWORD)
                        .param("matchingPassword", PASSWORD)
                        .param("firstName", FIRST_NAME)
                        .param("lastName", LAST_NAME)
                        .param("email", EMAIL)
                        .param("formRole", "ROLE_EMPLOYEE"))
                .andExpect(status().isOk())
                .andExpect(view().name("security/registration-confirmation"));

        then(userService).should().saveUser(any(RegisterUser.class), anyList());
    }
}