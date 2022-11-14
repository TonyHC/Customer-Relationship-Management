package com.crm.customertracker.service;

import com.crm.customertracker.entity.security.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {
    public static final String USERNAME = "TJ3";

    @Mock
    private UserService userService;

    @InjectMocks
    private ImageServiceImpl imageService;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().username(USERNAME).build();
    }

    @DisplayName("Save a image byte array to associated user")
    @Test
    void saveImage() throws IOException {
        given(userService.findByUserName(anyString())).willReturn(user);

        MockMultipartFile multipartFile = new MockMultipartFile("imageFile", "testing.txt",
                "text/plain", "Some text to test".getBytes());

        imageService.saveImage(USERNAME, multipartFile);

        then(userService).should().saveUser(userArgumentCaptor.capture());

        User savedUser = userArgumentCaptor.getValue();
        assertThat(multipartFile.getBytes().length).isEqualTo(savedUser.getImage().length);
    }
}