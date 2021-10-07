package com.crm.customertracker.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crm.customertracker.utils.CustomersMethodCall;

@Aspect
@Component
public class AnalyticsAspect {
	// Logger for AnalyticsAspect Class
	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Autowired
	private CustomersMethodCall customerMethodCalls;
	
	@Around("com.crm.customertracker.aspect.PointcutDeclarations.NumberOfCallsForSaveCustomer()")
	public void saveCustomerCalls(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		// Display Method we are calling
		String methodSignature = proceedingJoinPoint.getSignature().toShortString();
		logger.info("Method: " + methodSignature);
		
		// Execute the method
		proceedingJoinPoint.proceed();
	
		// Increase the number of calls to this method by one and log the result
		customerMethodCalls.numberOfMethodCalls();
		logger.info("saveCustomer() Calls: " + String.valueOf(customerMethodCalls.getSaveCustomerCalls()));
	}
}