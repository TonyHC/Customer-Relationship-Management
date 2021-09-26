package com.java.springdemo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.java.springdemo.entity.User;
import com.java.springdemo.user.RegisterUser;

public interface UserService extends UserDetailsService{
    User findByUserName(String userName);
    void saveUser(RegisterUser registerUser, List<String> roles);
}
