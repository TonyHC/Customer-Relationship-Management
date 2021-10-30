DROP DATABASE IF EXISTS spring_security_custom_user;
CREATE DATABASE IF NOT EXISTS spring_security_custom_user;
USE spring_security_custom_user;

DROP TABLE IF EXISTS user;

CREATE TABLE user (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(50) NOT NULL,
    	password char(80) NOT NULL,
    	first_name VARCHAR(50) NOT NULL,
    	last_name VARCHAR(50) NOT NULL,
    	email VARCHAR(50) NOT NULL,
	image MEDIUMBLOB
);

INSERT INTO user (username, password, first_name, last_name, email) VALUES 
	('Steve','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','Steve','Noles','steno@gmail.com'),
	('rob@gmail.com','$2a$12$ZQ7ZB8IQplDGcQnknYWRq.JKk1nTZ18I.SwA1sVanHYilR9eDuDAm','Rob','Public','RP@gmail.com'),
	('Mary','$2a$04$eFytJDGtjbThXa80FyOOBuFdK2IwjyWefYkMpiBEFlpBwDH.5PM0K','Mary','Adams','maryadams6@hotmail.com');

DROP TABLE IF EXISTS role;

CREATE TABLE role (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

INSERT INTO role (name) VALUE
	('ROLE_EMPLOYEE'),('ROLE_MANAGER'),('ROLE_ADMIN');
    
DROP TABLE IF EXISTS users_roles;

CREATE TABLE users_roles (
	user_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY(user_id) REFERENCES user(id)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    FOREIGN KEY(role_id) REFERENCES role(id)
    ON DELETE NO ACTION ON UPDATE NO ACTION,
    PRIMARY KEY(user_id, role_id)
);

INSERT INTO users_roles (user_id,role_id) VALUES 
	(1, 1),
	(2, 1),
	(2, 2),
	(3, 1),
	(3, 3);