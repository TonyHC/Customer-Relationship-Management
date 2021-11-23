package com.crm.customertracker.aspect;

import com.crm.customertracker.utils.CustomersMethodCall;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class AnalyticsAspect {
	// Logger for AnalyticsAspect Class
	private final Logger logger = Logger.getLogger(getClass().getName());

	private final CustomersMethodCall customerMethodCalls;

	public AnalyticsAspect(CustomersMethodCall customerMethodCalls) {
		this.customerMethodCalls = customerMethodCalls;
	}

	@Around("com.crm.customertracker.aspect.PointcutDeclarations.NumberOfCallsForSaveCustomer()")
	public void saveCustomerCalls(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		// Display Method we are calling
		String methodSignature = proceedingJoinPoint.getSignature().toShortString();
		logger.info("Method: " + methodSignature);
		
		// Execute the method
		proceedingJoinPoint.proceed();
	
		// Increase the number of calls to this method by one and log the result
		customerMethodCalls.numberOfMethodCalls();
		logger.info("saveCustomer() Calls: " + customerMethodCalls.getSaveCustomerCalls());
	}
}