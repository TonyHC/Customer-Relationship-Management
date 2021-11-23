package com.crm.customertracker.config;

import com.crm.customertracker.entity.security.User;
import com.crm.customertracker.service.UserService;
import org.jboss.logging.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private final UserService userService;

	public CustomAuthenticationSuccessHandler(UserService userService) {
		this.userService = userService;
	}

	private final Logger logger = Logger.getLogger(getClass().getName());

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
										Authentication authentication) throws IOException {
		logger.info("In onAuthenticationSuccess()");

		String username = authentication.getName();
		logger.info("Username: " + username);

		// Find a User by its username
		User user = userService.findByUserName(username);

		// Place a User found in DB into an HTTP Session
		HttpSession session = request.getSession();
		session.setAttribute("user", user);

		// Forward that User to employees mapping when authentication (logging in) was successfully
		response.sendRedirect(request.getContextPath() + "/employees/page");
	}
}