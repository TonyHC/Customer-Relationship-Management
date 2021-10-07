package com.crm.customertracker.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Override
	public boolean isValid(final String email, final ConstraintValidatorContext context) {
		// Compile the Regular Expression from String to Pattern object
		pattern = Pattern.compile(EMAIL_PATTERN);
		
		// If Email input form data is empty, return false (not valid)
		if (email == null) {
			return false;
		}
		
		// Match the Email input form data against the pattern
		matcher = pattern.matcher(email);
		
		// Return the results
		return matcher.matches();
	}
}