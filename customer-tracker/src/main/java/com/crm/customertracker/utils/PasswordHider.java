package com.crm.customertracker.utils;

import org.springframework.stereotype.Component;

@Component
public class PasswordHider {
    public String passwordMasking(String password) {
        return password.replaceAll(".", "*");
    }
}