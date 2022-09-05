package com.crm.customertracker.controller;

import com.crm.customertracker.entity.security.User;
import com.crm.customertracker.service.ImageService;
import com.crm.customertracker.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ImageControllerTest {
    @Mock
    ImageService imageService;

    @Mock
    UserService userService;

    @InjectMocks
    ImageController imageController;

    MockMvc mockMvc;
    User user;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();

        user = User.builder().id(1).firstName("Tom").username("TomJ").build();

        given(userService.retrieveAuthenticatedPrincipalByUsername()).willReturn(user);
    }

    @DisplayName("Display image upload form for registered users")
    @Test
    void showImageUploadForm() throws Exception {
        mockMvc.perform(get("/employees/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("employees/imageUploadForm"))
                .andExpect(model().attributeExists("firstName"));
    }

    @DisplayName("Process and save image byte array to database")
    @Test
    void processImageUploadForm() throws Exception {
        MockMultipartFile multipartFile = new MockMultipartFile("imageFile", "testing.txt",
                "text/plain", "Some text to test".getBytes());

        mockMvc.perform(multipart("/employees/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/employees/profile"));

        then(imageService).should().saveImage(anyString(), any());
    }

    @DisplayName("Render image byte array from specified registered user")
    @Test
    void renderImageFromDB() throws Exception {
        String testString = "Some text to test";
        Byte[] imageBytes = new Byte[testString.getBytes().length];

        int currentBytes = 0;
        for (byte primitiveByte : testString.getBytes()) {
            imageBytes[currentBytes++] = primitiveByte;
        }

        user.setImage(imageBytes);

        MockHttpServletResponse response = mockMvc.perform(get("/employees/profileImage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();
        assertThat(testString.getBytes().length).isEqualTo(responseBytes.length);
    }
}