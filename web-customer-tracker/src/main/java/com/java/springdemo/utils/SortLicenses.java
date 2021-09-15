package com.java.springdemo.utils;

import org.springframework.stereotype.Component;

@Component
public class SortLicenses implements SortUtils {
	@Override
	public String fieldName(int sortField) {
		String fieldName = null;
		
		switch(sortField) {
			case SortUtils.LICENSE_NAME:
				fieldName = "licenseName, expirationDate";
				break;
			case SortUtils.START_DATE:
				fieldName = "startDate";
				break;
			case SortUtils.EXPIRATION_DATE:
				fieldName = "expirationDate";
				break;
			default:
				fieldName = "expirationDate";
		}
		
		return fieldName;
	}
}