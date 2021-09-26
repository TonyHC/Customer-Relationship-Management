package com.java.springdemo.dao;

import com.java.springdemo.entity.User;

public interface UserDAO {
    User findByUserName(String userName);
    void save(User user);
}
