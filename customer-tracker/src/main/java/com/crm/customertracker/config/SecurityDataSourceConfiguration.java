package com.crm.customertracker.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.crm.customertracker.entity.security.Role;
import com.crm.customertracker.entity.security.User;

@Configuration	
@EnableJpaRepositories(basePackages = "com.crm.customertracker.repository.security",
		entityManagerFactoryRef = "securityEntityManagerFactory",
		transactionManagerRef = "securityTransactionManager")
public class SecurityDataSourceConfiguration {
	@Bean
	@ConfigurationProperties("app.datasource.security")
	public DataSourceProperties securityDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean
	@ConfigurationProperties("app.datasource.security.configuration")
	public DataSource securityDataSource() {
		return securityDataSourceProperties().initializeDataSourceBuilder()
				.type(BasicDataSource.class).build();
	}
	
	@Bean(name = "securityEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean securityEntityManagerFactory(EntityManagerFactoryBuilder builder) {
		return builder.dataSource(securityDataSource())
				.packages(User.class, Role.class)
				.build();
	}
	
	@Bean
	PlatformTransactionManager securityTransactionManager(
			final @Qualifier("securityEntityManagerFactory") LocalContainerEntityManagerFactoryBean securityEntityManagerFactory) {
		return new JpaTransactionManager(securityEntityManagerFactory.getObject());
	}
}