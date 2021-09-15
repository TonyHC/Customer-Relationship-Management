DROP SCHEMA IF EXISTS web_customer_tracker;
CREATE SCHEMA web_customer_tracker;
USE web_customer_tracker;

DROP TABLE IF EXISTS customer;

CREATE TABLE customer (
	id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
	first_name varchar(45) DEFAULT NULL,
	last_name varchar(45) DEFAULT NULL,
	email varchar(45) DEFAULT NULL
);

INSERT INTO customer(first_name, last_name, email) VALUES 
    ('David','Adams','david@luv2code.com'),
	('John','Doe','john@luv2code.com'),
	('Tommy','Rao','ajay@luv2code.com'),
	('Mary','Jones','mary@luv2code.com'),
	('Maxwell','Dixon','max@luv2code.com');
    
DROP TABLE IF EXISTS license;
    
CREATE TABLE license (
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    license_name VARCHAR(100) DEFAULT NULL,
    start_date DATETIME DEFAULT NULL,
    expiration_date DATETIME DEFAULT NULL,
    customer_id INT NOT NULL,
    FOREIGN KEY(customer_id) REFERENCES customer(id)
);
    
INSERT INTO license(license_name, start_date, expiration_date, customer_id) 
	VALUES ('Teaching', '2012/09/21 14:21:59', '2022/09/21 14:21:59', 3),
		('Indesign', '2016/11/09 23:11:31', '2025/11/09 23:11:31', 5),
        ('Illustrator', '2010/12/15 05:41:32', '2020/12/15 05:41:32', 4),
        ('Photoshop', '2014/06/30 03:42:45', '2020/06/30 03:42:45', 1),
		('Dreamweaver', '2014/06/30 07:14:25', '2025/06/30 07:14:25', 1),
		('Indesign', '2014/06/30 11:56:44', '2019/06/30 11:56:44', 1);
        
