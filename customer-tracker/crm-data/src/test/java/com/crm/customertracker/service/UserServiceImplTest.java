package com.crm.customertracker.service;

import com.crm.customertracker.entity.security.RegisterUser;
import com.crm.customertracker.entity.security.Role;
import com.crm.customertracker.entity.security.User;
import com.crm.customertracker.repository.security.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    public static final String EMAIL = "rj@gmail.com";
    public static final String USER_NAME = "admin";
    public static final String FIRST_NAME = "Ray";
    public static final String LAST_NAME = "Jones";
    public static final String ROLE = "EMPLOYEE";
    public static final String PASSWORD = "pass123";

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @BeforeEach
    void setUp() {
    }

    @DisplayName("Load all users from database successfully")
    @Test
    void loadUserByUsernameSuccessfully() {
        given(userRepository.findByUserName(anyString()))
                .willReturn(User.builder().username(USER_NAME).password("admin")
                        .roles(List.of(Role.builder().name("EMPLOYEE").build())).build());

        UserDetails userDetails = userService.loadUserByUsername(anyString());

        assertThat(userDetails).isNotNull();
    }

    @DisplayName("Failed to load all users from database")
    @Test
    void loadUserByUsernameFailed() {
        given(userRepository.findByUserName(anyString())).willReturn(null);

        assertThrows(UsernameNotFoundException.class, () ->userService.loadUserByUsername(anyString()));
    }

    @DisplayName("Find a user by its username")
    @Test
    void findByUserName() {
        given(userRepository.findByUserName(anyString())).willReturn(User.builder().build());

        User returnedUser = userService.findByUserName(anyString());

        assertThat(returnedUser).isNotNull();
    }

    @DisplayName("Save a user")
    @Test
    void saveUser() {
        userService.saveUser(any());

        then(userRepository).should().save(any());
    }

    @DisplayName("Save a user from registration form")
    @Test
    void saveUserFromRegistrationForm() {
        RegisterUser registerUser =
                RegisterUser.builder().userName(USER_NAME).password(PASSWORD).firstName(FIRST_NAME).lastName(LAST_NAME)
                        .email(EMAIL).formRole(ROLE).build();

        userService.saveUser(registerUser, anyList());

        then(userRepository).should().save(userArgumentCaptor.capture());

        User savedUser = userArgumentCaptor.getValue();
        assertAll(
                () -> assertThat(savedUser).isNotNull(),
                () -> assertThat(savedUser.getUsername()).isEqualTo(USER_NAME),
                () -> assertThat(savedUser.getFirstName()).isEqualTo(FIRST_NAME),
                () -> assertThat(savedUser.getLastName()).isEqualTo(LAST_NAME),
                () -> assertThat(savedUser.getEmail()).isEqualTo(EMAIL),
                () -> assertThat(savedUser.getRoles()).isNotNull()
        );
    }
}