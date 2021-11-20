package com.crm.customertracker.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.crm.customertracker.entity.security.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	// Use @Query to create Custom Query to get a Role based on its name
	@Query("SELECT r FROM Role r WHERE r.name = ?1")
	Role findRoleByName(String roleName);
}