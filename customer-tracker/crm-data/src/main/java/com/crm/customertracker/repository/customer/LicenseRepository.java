package com.crm.customertracker.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.customertracker.entity.customer.License;

public interface LicenseRepository extends JpaRepository<License, Integer> {

}