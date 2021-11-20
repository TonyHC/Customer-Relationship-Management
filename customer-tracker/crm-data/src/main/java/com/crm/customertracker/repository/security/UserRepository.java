package com.crm.customertracker.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crm.customertracker.entity.security.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	// Use @Query to create Custom Query to get a User along with its Roles by the username
	@Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.username = ?1")
	User findByUserName(String username);
}