package com.crm.customertracker.controller;

import com.crm.customertracker.entity.security.User;
import com.crm.customertracker.service.ImageService;
import com.crm.customertracker.service.UserService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {
    private final ImageService imageService;
    private final UserService userService;

    public ImageController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }

    @GetMapping("/employees/image")
    public String showImageUploadForm(Model model) {
        // Obtain the authenticated User from User Service
        User user = userService.retrieveAuthenticatedPrincipalByUsername();

        // Add Authenticated User properties to Model Attribute
        model.addAttribute("firstName", user.getFirstName());

        return "employees/imageUploadForm";
    }

    @PostMapping("/employees/image")
    public String processImageUploadForm(@RequestParam("imageFile") MultipartFile file) {
        // Retrieved the Authenticated User's Username
        String username = userService.retrieveAuthenticatedPrincipalByUsername().getUsername();

        // Call the Image Service's saveImage(...) to save the Image uploaded by the User
        imageService.saveImage(username, file);

        return "redirect:/employee/profile";
    }

    @GetMapping("/employees/profileImage")
    public void renderImageFromDB(HttpServletResponse response) throws IOException {
        // Obtain the authenticated User from User Service
        User user = userService.retrieveAuthenticatedPrincipalByUsername();

        // If the User has uploaded an Image
        if (user.getImage() != null) {
            // Create a primitive byte array of same size as number of bytes in the image uploaded
            byte[] byteArray = new byte[user.getImage().length];

            // Convert the wrapper Bytes to primitive bytes
            int i = 0;
            for (Byte wrappedByte : user.getImage()){
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            // Set the HttpServletResponse as image type of jpeg
            response.setContentType("image/jpeg");

            // Set the InputStream as the bytes of the uploaded Image
            InputStream is = new ByteArrayInputStream(byteArray);

            // Render the Uploaded Image byte's to output the Image to the User's Profile Page
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}