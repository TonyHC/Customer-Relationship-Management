package com.crm.customertracker.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.crm.customertracker.entity.security.User;
import com.crm.customertracker.service.UserService;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	private UserService userService;
	
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.info("In onAuthenticationSuccess()");

		String username = authentication.getName();
		logger.info("Username: " + username);
		
		// Find a User by its username
		User user = userService.findByUserName(username);
		
		// Place a User found in DB into a HTTP Session
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		
		// Forward that User to employees mapping when authentication (logging in) was successfully
		response.sendRedirect(request.getContextPath() + "/employees");
	}
}