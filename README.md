# CRM
Web implementation of Customer Relationship Management (CRM)

### Basic Features
Basic CRUD operations for customer is accessible with basic authorization level after authentication <br />
Search for customer(s) by either their first or last name and results are sorted by last name in ascending order <br />
Read and delete a customer's license is limited to certain higher authorization level after authentication

## Version 1: web-customer-tracker 
Uses the following:
- Spring 5
- JSP
- Hibernate 
- Spring Security (using MySQL)
- AOP 
- Maven

## Version 2: customer-tracker 
Refactor and update version 1 code to make use of the following:
- Spring Boot 2 
- Thymeleaf 
- Data JPA (Hibernate using MySQL) 
- Spring Security (using MySQL)
- AOP 
- Maven

### Aditional Features
Version 2 has the following additional features from version 1:
- Add pagination support when displaying the list of customer or customer licenses data in table format
- Add image upload feature to allow authenticated users to change their user profile image when a valid jpeg image is uploaded
