package com.java.springdemo.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@PropertySource({"classpath:persistence-mysql.properties", "classpath:security-persistence-mysql.properties"})
@ComponentScan(basePackages = "com.java.springdemo")
public class AppConfig implements WebMvcConfigurer {
	@Autowired
	private Environment env;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	// Define a Bean for View Resolver 
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}
	
	// Define a Bean for Web Customer Tracker Data Source
	@Bean
	public DataSource myDataSource() {
		// Create Connection Source
		ComboPooledDataSource myDataSource = new ComboPooledDataSource();
		
		try {
			myDataSource.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException exception) {
			throw new RuntimeException(exception);
		}
		
		// For sanity, log url and user just to make sure we are reading the data
		logger.info("jdbc.url = " + env.getProperty("jdbc.url"));
		logger.info("jdbc.user = " + env.getProperty("jdbc.user"));
		
		// Set Database Connection Properties
		myDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
		myDataSource.setUser(env.getProperty("jdbc.user"));
		myDataSource.setPassword(env.getProperty("jdbc.password"));
		
		// Set Connection Pool Properties
		myDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		myDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		myDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		myDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
	
		return myDataSource;
	}
	
	// Define a Bean for Security Data Source
	@Bean
	public DataSource securityDataSource() {
		// Create Connection Source
		ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
		
		try {
			securityDataSource.setDriverClass(env.getProperty("security.jdbc.driver"));
		} catch (PropertyVetoException exception) {
			throw new RuntimeException(exception);
		}
		
		// For sanity, log url and user just to make sure we are reading the data
		logger.info("security.jdbc.url = " + env.getProperty("security.jdbc.url"));
		logger.info("security.jdbc.user = " + env.getProperty("security.jdbc.user"));
		
		// Set Database Connection Properties
		securityDataSource.setJdbcUrl(env.getProperty("security.jdbc.url"));
		securityDataSource.setUser(env.getProperty("security.jdbc.user"));
		securityDataSource.setPassword(env.getProperty("security.jdbc.password"));
		
		// Set Connection Pool Properties
		securityDataSource.setInitialPoolSize(getIntProperty("security.connection.pool.initialPoolSize"));
		securityDataSource.setMinPoolSize(getIntProperty("security.connection.pool.minPoolSize"));
		securityDataSource.setMaxPoolSize(getIntProperty("security.connection.pool.maxPoolSize"));
		securityDataSource.setMaxIdleTime(getIntProperty("security.connection.pool.maxIdleTime"));
	
		return securityDataSource;
	}

	// Helper Method: Read Environment Property and convert to int
	private int getIntProperty(String propertyName) {
		String propertyValue = env.getProperty(propertyName);
		int intPropertyValue = Integer.parseInt(propertyValue);
		return intPropertyValue;
	}
	
	// Define the SessionFactory
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		// Create Session Factory
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	
		// Set the Properties
		sessionFactory.setDataSource(myDataSource());
		sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());
		
		return sessionFactory;
	
	}

	// Helper Method: Set Hibernate Properties and return those properties
	private Properties getHibernateProperties() {
		// Set Hibernate Properties
		Properties properties = new Properties();
		
		properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		
		return properties;
	}
	
	// Define the Security SessionFactory
	@Bean
	public LocalSessionFactoryBean securitySessionFactory() {
		// Create Session Factory
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	
		// Set the Properties
		sessionFactory.setDataSource(securityDataSource());
		sessionFactory.setPackagesToScan(env.getProperty("security.hibernate.packagesToScan"));
		sessionFactory.setHibernateProperties(getSecurityHibernateProperties());
		
		return sessionFactory;
	
	}

	// Helper Method: Set Hibernate Properties and return those properties
	private Properties getSecurityHibernateProperties() {
		// Set Hibernate Properties
		Properties properties = new Properties();
		
		properties.setProperty("hibernate.dialect", env.getProperty("security.hibernate.dialect"));
		properties.setProperty("hibernate.show_sql", env.getProperty("security.hibernate.show_sql"));
		
		return properties;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager customerTransactionManager(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
		// Setup Transaction Manager based on session factory
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		
		return transactionManager;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager securityTransactionManager(@Qualifier("securitySessionFactory") SessionFactory sessionFactory) {
		// Setup Transaction Manager based on session factory
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		
		return transactionManager;
	}
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/resources/**")
          .addResourceLocations("/resources/"); 
    }	
}