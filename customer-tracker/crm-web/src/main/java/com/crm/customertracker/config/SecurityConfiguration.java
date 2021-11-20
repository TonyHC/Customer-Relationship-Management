package com.crm.customertracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.crm.customertracker.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	// Contains the reference that uses the Security Data Source
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/customers/**").hasRole("EMPLOYEE")
			.antMatchers("/licenses/**").hasAnyRole("MANAGER", "ADMIN")
			.and()
			.formLogin()
				.loginPage("/login/showLoginPage")
				.loginProcessingUrl("/authenticateUser")
				.successHandler(customAuthenticationSuccessHandler)
				.permitAll()
			.and()
			.logout().logoutSuccessUrl("/").permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/login/accessDenied");
	}
	
	// Use BCryptPasswordEncoder to encode the password when we register a new User
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// A simple authentication provider that uses a Data Access Object (DAO) to  retrieve user information from a relational database. 
	// It leverages a UserDetailsService (as a DAO) in order to lookup the username, password and GrantedAuthority 
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}
}