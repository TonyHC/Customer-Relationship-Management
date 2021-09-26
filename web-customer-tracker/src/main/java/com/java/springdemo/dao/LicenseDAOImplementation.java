package com.java.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.java.springdemo.entity.License;
import com.java.springdemo.utils.SortUtils;

@Repository
public class LicenseDAOImplementation implements LicenseDAO {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier("sortLicenses")
	private SortUtils sortUtils;

	@Override
	public List<License> getLicenses(int sortField) {
		// Get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// Get the desired field name to sort License
		String fieldName = sortUtils.fieldName(sortField);
		
		// Create a query to get all Licenses from DB and order the query by desired field name (property)
		String queryLicense = "FROM License ORDER BY " + fieldName;
		Query<License> query = currentSession.createQuery(queryLicense, License.class);
		
		// Get the desired Licenses
		List<License> licenses = query.getResultList();
		
		// Return list of desired Licenses
		return licenses;
	}

	@Override
	public void deleteLicense(int licenseID) {
		// Get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// Create a query to retrieve the desired License based on its id (Primary Key passed in)
		Query<License> query = currentSession.createQuery("FROM License WHERE id =: theLicenseID", License.class);
		
		// Set 'theLicenseID' with value passed in
		query.setParameter("theLicenseID", licenseID);
		
		
		// Execute query and store the result
		License tmpLicense = query.getSingleResult();
		
		// Delete the retireve License from DB
		currentSession.delete(tmpLicense);
	}
}