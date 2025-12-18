DROP TABLE IF EXISTS visa_application, users, admins, owners, migrants;

CREATE TABLE visa_application(
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    state SMALLINT,
    name_owner VARCHAR(1000),
    passport_owner VARCHAR(1000),
    name_migrant VARCHAR(1000),
    passport_migrant VARCHAR(1000),
    citizenship_migrant VARCHAR(1000),
    address VARCHAR(1000),
    comment VARCHAR(1000)
);

CREATE TABLE users(
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(1000),
    password_hash INT
);

CREATE TABLE admins(
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(1000),
    password_hash INT
);

CREATE TABLE owners(
    user_id INT PRIMARY KEY,
    name VARCHAR(1000),
    passport VARCHAR(1000),
    address VARCHAR(1000)
);

CREATE TABLE migrants(
    name VARCHAR(1000),
    passport VARCHAR(1000) PRIMARY KEY,
    citizenship VARCHAR(1000)
);