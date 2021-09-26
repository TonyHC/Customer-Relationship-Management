package com.java.springdemo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.springdemo.dao.RoleDAO;
import com.java.springdemo.dao.UserDAO;
import com.java.springdemo.entity.Role;
import com.java.springdemo.entity.User;
import com.java.springdemo.user.RegisterUser;

@Service
public class UserServiceImplementation implements UserService {
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private RoleDAO roleDAO;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	@Transactional("securityTransactionManager")
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.findByUserName(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("Invalid Username or Password");
		}
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(), 
				user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	@Transactional("securityTransactionManager")
	public User findByUserName(String userName) {
		// Check if Username exists in DB
		return userDAO.findByUserName(userName);
	}

	@Override
	@Transactional("securityTransactionManager")
	public void saveUser(RegisterUser registerUser, List<String> roles) {
		User user = new User();
		
		user.setUsername(registerUser.getUserName());
		user.setPassword(passwordEncoder.encode(registerUser.getPassword()));
		user.setFirstName(registerUser.getFirstName());
		user.setLastName(registerUser.getLastName());
		user.setEmail(registerUser.getEmail());
		
		user.setRoles(userRoles(roles));

		userDAO.save(user);
	}
	
	private List<Role> userRoles(List<String> roles) {
		List<Role> userRoles = new ArrayList<>();
		for (String role : roles) {
			userRoles.add(roleDAO.findRoleByName(role));
		}
		return userRoles;
	}
}
