package com.java.springdemo.utils;

public interface SortUtils {
	public static final int FIRST_NAME = 1;
	public static final int LAST_NAME = 2;
	public static final int EMAIL = 3;
	public static final int LICENSE_NAME = 4;
	public static final int START_DATE = 5;
	public static final int EXPIRATION_DATE = 6;
	
	public String fieldName(int sortField);
}