package com.crm.customertracker.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PointcutDeclarations {	
	// Pointcut Declaration: Match on any Method with any number of parameters
	// in any Class within the Conroller package
	@Pointcut("execution(* com.crm.customertracker.controller.*.*(..))")
	public void controllerPackage() {}
	
	// Pointcut Declaration: Match on any Method with any number of parameters
	// in any Class within the Repository package
	@Pointcut("execution(* com.crm.customertracker.repository.*.*.*(..))")
	public void repositoryPackage() {}
	
	// Pointcut Declaration: Match on any Method with any number of parameters
	// in any Class within the Service package
	@Pointcut("execution(* com.crm.customertracker.service.*.*(..))")
	public void servicePackage() {}
	
	// Pointcut Declaration: Match on any Method that starts with search with any number
	// of parameters in any Class within all packages
	@Pointcut("execution (* com.crm.customertracker.*.*.search*(..))")
	public void searchCustomers() {}
	
	// Pointcut Declaration: Match on any Method that called saveCustomer with any number
	// of parameters in any Class within the Dao package
	@Pointcut("execution (* com.crm.customertracker.service.*.saveCustomer(..))")
	public void NumberOfCallsForSaveCustomer() {}
	
	// Combine Pointcut Declarations to match any Method with any number of parameters in
	// any Class within the Controller, Service, and Dao packages
	@Pointcut("controllerPackage() || servicePackage() || repositoryPackage()")
	public void webAppFlow() {}
}