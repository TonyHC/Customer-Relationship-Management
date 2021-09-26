package com.java.springdemo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.java.springdemo.entity.Role;

@Repository
public class RoleDAOImplementation implements RoleDAO {
	@Autowired
	@Qualifier("securitySessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public Role findRoleByName(String roleName) {
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query<Role> query = currentSession.createQuery("FROM Role WHERE name =: rName", Role.class);
		query.setParameter("rName", roleName);
		
		Role role = null;
		
		try {
			role = query.getSingleResult();
		} catch (Exception exception) {
			role = null;
		}
		
		return role;
	}
}
