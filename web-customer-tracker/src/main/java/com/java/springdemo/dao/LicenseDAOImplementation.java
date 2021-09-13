package com.java.springdemo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.java.springdemo.entity.License;

@Repository
public class LicenseDAOImplementation implements LicenseDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public List<License> getLicenses() {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<License> query = currentSession.createQuery("FROM License", License.class);
		
		List<License> licenses = query.getResultList();
		
		return licenses;
	}
}