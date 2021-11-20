package com.crm.customertracker.config;

import com.crm.customertracker.entity.customer.Customer;
import com.crm.customertracker.entity.customer.License;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.crm.customertracker.repository.customer",
		entityManagerFactoryRef = "customerEntityManagerFactory",
		transactionManagerRef = "customerTransactionManager")
public class CustomerDataSourceConfiguration {
	@Bean
	@Primary
	@ConfigurationProperties("app.datasource.customer")
	public DataSourceProperties customerDataSourceProperites() {
		return new DataSourceProperties();
	}
	
	@Bean
	@Primary
	@ConfigurationProperties("app.datasource.customer.configuration")
	public DataSource customerDataSource() {
		return customerDataSourceProperites().initializeDataSourceBuilder()
				.type(HikariDataSource.class).build();
	}
	
	@Primary
	@Bean(name = "customerEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean customerEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder
				.dataSource(customerDataSource())
				.packages(Customer.class, License.class)
				.build();
	}
	
	@Primary
	@Bean
	public PlatformTransactionManager customerTransactionManager(
			final @Qualifier("customerEntityManagerFactory") LocalContainerEntityManagerFactoryBean customerEntityManagerFactory) {
		return new JpaTransactionManager(customerEntityManagerFactory.getObject());
	}
}