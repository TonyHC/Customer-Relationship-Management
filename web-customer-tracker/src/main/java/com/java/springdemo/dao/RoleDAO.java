package com.java.springdemo.dao;

import com.java.springdemo.entity.Role;

public interface RoleDAO {
	Role findRoleByName(String roleName);
}
