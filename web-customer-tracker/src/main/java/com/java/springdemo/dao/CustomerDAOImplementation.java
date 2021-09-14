package com.java.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.java.springdemo.entity.Customer;

@Repository
public class CustomerDAOImplementation implements CustomerDAO {
	// Need to Inject the Session Factory from Spring Config File Bean ID [sessionFactory]
	// into the SessionFactory object 
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Customer> getCustomers() {
		// Get the Current Hibernate Session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// Create a Query for Customers and Sort Data By Last Name Alphabetically 
		Query<Customer> query = currentSession.createQuery("FROM Customer ORDER BY lastName", Customer.class);
		
		// Execute Query and Get Result List of Customers
		List<Customer> customers = query.getResultList();
		
		// Return List of Customers
		return customers;
	}
	
	@Override
	public void saveCustomer(Customer customer) {
		// Get the Current Hibernate Session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// Save/Update the Customer to the DB
		// If (Primary Key / ID) empty then Insert new Customer else Update existing Customer
		currentSession.saveOrUpdate(customer);
	}
	
	@Override
	public Customer getCustomer(int customerID) {
		// Get the Current Hibernate Session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// Get the Customer from DB using Primary Key (id passed in)
		Customer customer = currentSession.get(Customer.class, customerID);
		
		// Return desired Customer
		return customer;
	}
	
	@Override
	public void deleteCustomer(int customerID) {
		// Get the Current Hibernate Session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// Create a query to find the desired Customer using Primary Key (customerID passed in)
		Query<Customer> query = currentSession.createQuery("FROM Customer WHERE id =: theCustomerID", Customer.class);
		
		// Set 'theCustomerID' parameter to 'customerID' passed in
		query.setParameter("theCustomerID", customerID);
		
		// Get the desired Customer from query result
		Customer tmpCustomer = query.getSingleResult();
		
		// Remove all Customer's references to their Licenses, so deleting a Customer also deletes their Licenses
		tmpCustomer.deleteLicenses();
		
		// Delete the Customer along with its Licenses from DB
		currentSession.delete(tmpCustomer);
	}	
	
	@Override
	public Customer getCustomerLicense() {
		// Get the Current Hibernate Session
		Session session = sessionFactory.getCurrentSession();
		
		// Create a query for a specific Customer and its Licenses
		Query<Customer> query = session.createQuery("SELECT c FROM Customer c JOIN FETCH c.licenses WHERE c.id = 1", Customer.class);
		
		// Execute Query and Get Customer along with its Licenses
		Customer customer = query.getSingleResult();
	
		// Return the desired Customer
		return customer;
	}
}