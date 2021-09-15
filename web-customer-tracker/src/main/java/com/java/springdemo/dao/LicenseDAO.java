package com.java.springdemo.dao;

import java.util.List;

import com.java.springdemo.entity.License;

public interface LicenseDAO {
	public List<License> getLicenses(int sortField);
}