package com.java.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.java.springdemo.entity.Customer;
import com.java.springdemo.utils.SortUtils;

@Repository
public class CustomerDAOImplementation implements CustomerDAO {
	// Need to Inject the Session Factory from Spring Config File Bean ID [sessionFactory]
	// into the SessionFactory object 
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	@Qualifier("sortCustomers")
	private SortUtils sortUtils;
	
	@Override
	public List<Customer> getCustomers(int sortField) {
		// Get the Current Hibernate Session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// Determine the Sort Field
		String fieldName = sortUtils.fieldName(sortField);
		
		// Create a Query for Customers and Sort Data By Last Name Alphabetically 	
		String queryCustomer = "FROM Customer ORDER BY " + fieldName;
		Query<Customer> query = currentSession.createQuery(queryCustomer, Customer.class);
		
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
	public List<Customer> searchCustomers(String searchName) {
		// Get the current Hibernate Session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query query = null;
		
		// Only search by name if name is not null or empty (only white spaces)
		if (searchName != null && searchName.trim().length() > 0) {
			// Search by firstName or lastName of Customer (case insensitive)
			query = currentSession.createQuery("FROM Customer WHERE LOWER(firstName) LIKE :theName OR LOWER(lastName) LIKE :theName ORDER BY lastName", Customer.class);
			query.setParameter("theName", "%" + searchName.toLowerCase() + "%");
		} else {
			// SearchName is empty, we return all the Customers
			query = currentSession.createQuery("FROM Customer ORDER BY lastName", Customer.class);
		}
		
		// Execute query and get the desired Customer(s)
		List<Customer> customers = query.getResultList();
		
		// Return desired Customer(s)
		return customers;
	}	
	
	@Override
	public Customer getCustomerLicense(int customerID) {
		// Get the Current Hibernate Session
		Session session = sessionFactory.getCurrentSession();
		
		// Create a query for a specific Customer and its Licenses
		Query<Customer> query = session.createQuery("SELECT c FROM Customer c JOIN FETCH c.licenses WHERE c.id =: theCustomerID", Customer.class);
		query.setParameter("theCustomerID", customerID);
		
		Customer customer = null;
	
		// If query found no data, then we create new empty Customer
		if (query.uniqueResult() == null) {
			customer = new Customer();
	
		} else {
			// Execute Query and Get Customer along with its Licenses
			customer = query.getSingleResult();
		}
		
		// Return the desired Customer
		return customer;
	}
}