package com.java.springdemo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.java.springdemo.entity.User;

@Repository
public class UserDAOImplementation implements UserDAO {
	// Add reference to SessionFactory
	@Autowired
	@Qualifier("securitySessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public User findByUserName(String userName) {
		// Get the current session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// Query for User who matches userName passed in 
		Query<User> query = currentSession.createQuery("FROM User WHERE username =: uName", User.class);
		query.setParameter("uName", userName);
		
		// Get Query result of matching User
		User user = null;
		
		try {
			user = query.getSingleResult();
		} catch (Exception exception) {
			user = null;
		}
		
		return user;
	}

	@Override
	public void save(User user) {
		// Get the current sesssion
		Session currentSession = sessionFactory.getCurrentSession();
		// Save or update User if User exists in DB or not through Primary Key
		currentSession.saveOrUpdate(user);
	}

}
