package com.java.springdemo.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
// Applied to a class marks this class as a candidate for mapping to the database
@Documented 
public @interface ValidEmail {
	String message() default "Invalid Email";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
