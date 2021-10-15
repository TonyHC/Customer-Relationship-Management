package com.crm.customertracker.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.crm.customertracker.entity.security.User;
import com.crm.customertracker.model.RegisterUser;

public interface UserService extends UserDetailsService {
    User findByUserName(String userName);
    void saveUser(RegisterUser registerUser, List<String> roles);
}