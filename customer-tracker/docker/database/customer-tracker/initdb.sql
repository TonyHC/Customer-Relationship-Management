CREATE DATABASE IF NOT EXISTS `web_customer_tracker`;
USE web_customer_tracker;

CREATE USER 'root'@'%' IDENTIFIED BY 'password';
GRANT ALL ON *.* TO 'root'@'%';

CREATE TABLE customer (
    id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name varchar(45) DEFAULT NULL,
    last_name varchar(45) DEFAULT NULL,
    email varchar(45) DEFAULT NULL
) AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO customer(first_name, last_name, email) VALUES
    ('David','Adams','david@gmail.com'),
    ('John','Doe','johndoe2@yahoo.com'),
    ('Tommy','Rao','Ratos@hotmail.com'),
    ('Mary','Jones','maryJ@gmail.com'),
    ('Maxwell','Dixon','maxWells@gmail.com');

CREATE TABLE license (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    license_name VARCHAR(100) DEFAULT NULL,
    start_date DATETIME DEFAULT NULL,
    expiration_date DATETIME DEFAULT NULL,
    customer_id INT NOT NULL,
    FOREIGN KEY(customer_id) REFERENCES customer(id)
) AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO license(license_name, start_date, expiration_date, customer_id) VALUES
    ('Teaching', '2012/09/21 14:21:59', '2022/09/21 14:21:59', 3),
    ('Indesign', '2016/11/09 23:11:31', '2025/11/09 23:11:31', 5),
    ('Illustrator', '2010/12/15 05:41:32', '2020/12/15 05:41:32', 4),
    ('Photoshop', '2014/06/30 03:42:45', '2020/06/30 03:42:45', 1),
    ('Dreamweaver', '2014/06/30 07:14:25', '2025/06/30 07:14:25', 1),
    ('Indesign', '2014/06/30 11:56:44', '2019/06/30 11:56:44', 1);