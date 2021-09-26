package com.java.springdemo.config;

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

import com.java.springdemo.entity.User;
import com.java.springdemo.service.UserService;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserService userService;
    
    private Logger logger = Logger.getLogger(getClass().getName());
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		logger.info("\nIn customAuthenticationSuccessHandler\n");

		String userName = authentication.getName();
		logger.info("userName: " + userName);

		User theUser = userService.findByUserName(userName);
		
		// Place a User found in DB into a HTTP Session
		HttpSession session = request.getSession();
		session.setAttribute("user", theUser);
		
		// Forward that User to employees mapping when authentication (logging in) was successfully
		response.sendRedirect(request.getContextPath() + "/employees");
	}
}
