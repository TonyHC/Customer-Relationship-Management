package com.java.springdemo.utils;

import org.springframework.stereotype.Component;

@Component
public class CustomersMethodCall implements MethodCalls {
	private int saveCustomerCalls = 0;
	
	@Override
	public int numberOfMethodCalls() {
		return saveCustomerCalls++;
	}

	public int getSaveCustomerCalls() {
		return saveCustomerCalls;
	}
}