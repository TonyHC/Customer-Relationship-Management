package com.crm.customertracker.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImage(String username, MultipartFile file);
}