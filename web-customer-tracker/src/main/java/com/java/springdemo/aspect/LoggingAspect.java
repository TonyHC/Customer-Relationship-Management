package com.java.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	// Setup Logger
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Before("com.java.springdemo.aspect.AspectDeclarations.webAppFlow()")
	public void beforeWebAppFlow(JoinPoint joinPoint) {
		// Display Method Signature we are calling
		String methodSignature = joinPoint.getSignature().toShortString();
		logger.info("@Before: " + methodSignature);
		
		// Get the Method Arguments
		Object[] args = joinPoint.getArgs();
		
		// Display the Method Arguments
		for (Object arg : args) {
			System.out.println("Method Argument: " + arg);
		}
	}
	
	@AfterReturning(pointcut = "com.java.springdemo.aspect.AspectDeclarations.webAppFlow()",
			returning = "result")
	public void afterReturningWebAppFlow(JoinPoint joinPoint, Object result) {
		// Display Method we are returning from
		String methodSignature = joinPoint.getSignature().toShortString();
		logger.info("@AfterReturning: " + methodSignature);
		
		// Display Data Returned
		logger.info("Result: " + result);
	}
	
	@Around("com.java.springdemo.aspect.AspectDeclarations.searchCustomers()")
	public Object searchCustomersDuration(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		// Display Method we are calling
		String methodSignature = proceedingJoinPoint.getSignature().toShortString();
		logger.info("@Around: " + methodSignature);
		
		// Get begin timestamp
		long begin = System.currentTimeMillis();
		
		Object result = null;
		
		try {
			// Execute the method and stored the return type of method calling
			result = proceedingJoinPoint.proceed();
		} catch (Exception exception) {
			// Log the exception
			logger.warning("Exception: " + exception);
		}
		
		// Get end timestamp
		long end = System.currentTimeMillis();
		
		// Compute the duration and display duration 
		long duration = end - begin;
		logger.info("Time Duration: " + duration + " milliseconds");
		
		// Return the resulting method calling return type
		return result;
	}
}