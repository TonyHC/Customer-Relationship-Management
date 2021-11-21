package com.crm.customertracker.entity.security;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.crm.customertracker.validator.FieldMatch;
import com.crm.customertracker.validator.ValidEmail;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldMatch.List ({@FieldMatch(first = "password", second = "matchingPassword", message = "The password field must match")})
public class RegisterUser {
	@NotNull(message="Username is required")
	@Size(min=1, message="is required")	
	private String userName;
	
	@NotNull(message="Password is required")
	@Size(min=1, message="is required")
	private String password;
	
	@NotNull(message="Confirmation Password is required")
	@Size(min=1, message="is required")
	private String matchingPassword;
	
	@NotNull(message="First Name is required")
	@Size(min=1, message="is required")
	private String firstName;
	
	@NotNull(message="Last Name is required")
	@Size(min=1, message="is required")
	private String lastName;
	
	@ValidEmail
	@NotNull(message="Email is required")
	@Size(min=1, message="is required")
	private String email;
	
	private String formRole;
}