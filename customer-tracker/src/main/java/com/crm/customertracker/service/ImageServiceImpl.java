package com.crm.customertracker.service;

import com.crm.customertracker.entity.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    private final UserService userService;

    public ImageServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void saveImage(String username, MultipartFile file) {
        try {
            User user = userService.findByUserName(username);

            Byte[] imageBytes = new Byte[file.getBytes().length];

            // MultipartFile use primitive byte[]
            int currentByte = 0;
            for (byte fileByte : file.getBytes()) {
                imageBytes[currentByte++] = fileByte;
            }

            user.setImage(imageBytes);

            userService.saveUser(user);
        } catch (IOException exception) {
            log.error("Error: " + exception);
            exception.printStackTrace();
        }
    }
}