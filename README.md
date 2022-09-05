# Customer Relationship Management (CRM)
Web implementation of Customer Relationship Management (CRM)

### Basic Features

Both Web Customer Tracker and Customer Tracker have the following features:
- Basic CRUD operations for a customer are accessible with a basic authorization level after authentication
- Search for customers by either their first or last name (*results sorted by their last name in ascending order*)
- Viewing and deleting an existing customer license is limited to certain users with specific authorization roles

## Web Customer Tracker
Uses the following:
- Spring 5
- JSP
- Hibernate
- Spring Security
- AOP
- Maven
- MySQL

## Customer Tracker
Refactor and update Web Customer Tracker to develop Customer Tracker to make use of the following:
- Multi-module project with Maven
- Spring Boot 2
- Thymeleaf
- Hibernate
- Data JPA
- Spring Security
- AOP
- Maven
- MySQL

### Additional Features
Customer Tracker has the following exclusive features from Web Customer Tracker:
- Pagination support when displaying the list of customer or customer licenses data in a table
- Image upload to allow authenticated users to change their profile image when a valid jpeg image is uploaded

### How to run this web application and its required services on Docker
**Prerequisites**
- Clone or download this repository through your preferred method

- Make sure you have Docker or Docker Desktop installed on your machine

- Open your preferred terminal and change to the *customer-tracker* directory containing the *docker-compose.yml*
  - Enter the following Docker command: `docker compose up -d` to create the required containers
  - Wait for the containers to be built and finish running

<br>**Docker containers are up and running**
- Head to [Customer Tracker](http://localhost:8000) to access the web application

- Visit [phpMyAdmin](http://localhost:8081) to access the GUI for MySQL and log in with the following credentials:
  - `Username: root`
  - `Password: password`
