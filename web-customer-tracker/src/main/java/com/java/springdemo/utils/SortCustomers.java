package com.java.springdemo.utils;

import org.springframework.stereotype.Component;

@Component
public class SortCustomers implements SortUtils {
	@Override
	public String fieldName(int sortField) {
		String fieldName = null;
		
		switch(sortField) {
			case SortUtils.FIRST_NAME:
				fieldName = "firstName";
				break;
			case SortUtils.LAST_NAME:
				fieldName = "lastName";
				break;
			case SortUtils.EMAIL:
				fieldName = "email";
				break;
			default:
				fieldName = "lastName";
		}
		
		return fieldName;
	}
}