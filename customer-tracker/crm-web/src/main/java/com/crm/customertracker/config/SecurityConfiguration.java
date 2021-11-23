package com.crm.customertracker.config;

import com.crm.customertracker.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	// Contains the reference that uses the Security Data Source
	private final UserService userService;
	private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	public SecurityConfiguration(UserService userService, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
		this.userService = userService;
		this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/employees/**").hasRole("EMPLOYEE")
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

	// A simple authentication provider that uses a Data Access Object (DAO) to  retrieve user information from a relational database.
	// It leverages a UserDetailsService (as a DAO) in order to look up the username, password and GrantedAuthority
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		// Use BCryptPasswordEncoder to encode the password when we register a new User
		auth.setPasswordEncoder(new BCryptPasswordEncoder());
		return auth;
	}
}