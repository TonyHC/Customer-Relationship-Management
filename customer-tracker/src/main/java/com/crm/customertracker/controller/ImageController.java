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

    @GetMapping("/employee/image")
    public String showImageUploadForm(Model model) {
        // Obtain the authenticated User from User Service
        User user = userService.retrieveAuthenticatedPrincipalByUsername();

        // Add Authenticated User properties to Model Attribute
        model.addAttribute("firstName", user.getFirstName());

        return "employees/imageUploadForm";
    }

    @PostMapping("/employee/image")
    public String processImageUploadForm(@RequestParam("imageFile") MultipartFile file) {
        String username = userService.retrieveAuthenticatedPrincipalByUsername().getUsername();

        imageService.saveImage(username, file);

        return "redirect:/employee/profile";
    }

    @GetMapping("/employee/profileImage")
    public void renderImageFromDB(HttpServletResponse response) throws IOException {
        User user = userService.retrieveAuthenticatedPrincipalByUsername();

        if (user.getImage() != null) {
            byte[] byteArray = new byte[user.getImage().length];
            int i = 0;

            for (Byte wrappedByte : user.getImage()){
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}