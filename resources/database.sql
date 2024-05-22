CREATE TABLE IF NOT EXISTS donors (
    donor_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    food BOOLEAN NOT NULL,
    first_aid BOOLEAN NOT NULL,
    clothing BOOLEAN NOT NULL,
    time BOOLEAN NOT NULL,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    zip VARCHAR(255) NOT NULL,
    login_id VARCHAR(255) NOT NULL
);

-- Create the beneficiaries table if it doesn't exist
CREATE TABLE IF NOT EXISTS beneficiaries (
    beneficiary_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    food BOOLEAN NOT NULL,
    first_aid BOOLEAN NOT NULL,
    clothing BOOLEAN NOT NULL,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    zip VARCHAR(255) NOT NULL,
    login_id VARCHAR(255) NOT NULL
);

-- Create the non_profit_organizations table if it doesn't exist
CREATE TABLE IF NOT EXISTS non_profit_organizations (
    org_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    ein VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20),
    address VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS non_profit_organizations_icon (
    org_id INT AUTO_INCREMENT PRIMARY KEY,
    icon_path VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS visits (
    entity_type VARCHAR(20),
    entity_id INT,
    visit_count INT DEFAULT 0,
    PRIMARY KEY (entity_type, entity_id)
);

-- Insert sample data into donors
INSERT INTO donors (first_name, last_name, email, phone_number, food, first_aid, clothing, time, street, city, state, zip, login_id) VALUES
('John', 'Doe', 'john@email.com', '000-000-0000', TRUE, FALSE, TRUE, FALSE, '3355 W Monroe St', 'Chicago', 'IL', '60624', '111111'),
('Jane', 'Smith', 'jane@email.com', '111-111-1111', FALSE, TRUE, FALSE, TRUE, '1821 W Erie St', 'Chicago', 'IL', '60622', '333333'),
('Emily', 'Jones', 'emily@email.com', '222-222-2222', TRUE, TRUE, TRUE, FALSE, '1648 S Allport St', 'Chicago', 'IL', '60608', '555555');

-- Insert sample data into beneficiaries
INSERT INTO beneficiaries (first_name, last_name, email, phone_number, food, first_aid, clothing, street, city, state, zip, login_id) VALUES
('Michael', 'Brown', 'michael@email.com', '333-333-3333', TRUE, FALSE, TRUE, '3353 W Monroe St', 'Chicago', 'Illinois', '60624', '222222'),
('Lisa', 'White', 'lisa@email.com', '444-444-4444', FALSE, TRUE, FALSE, '1825 W Erie St', 'Chicago', 'Illinois', '60622', '444444'),
('David', 'Wilson', 'david@email.com', '555-555-5555', TRUE, TRUE, TRUE, '1640 S Allport St', 'Chicago', 'Illinois', '60608', '666666');

-- Insert sample data into non_profit_organizations
INSERT INTO non_profit_organizations (name, ein, email, phone_number, address) VALUES
('Global Help', 123456789, 'contact@globalhelp.org', '666-666-6666', '53 W Jackson Blvd, Chicago, IL 60604'),
('Health and Help', 123456789, 'contact@healthandhelp.org', '777-777-7777', '538 S Dearborn St, Chicago, IL 60605'),
('Free Education', 123456789, 'contact@freeeducation.org', '888-888-8888', '218 S Wabash Ave, Chicago, IL 60604');

-- Insert icon data into non_profit_organizations_icon
INSERT INTO non_profit_organizations_icon (icon_path) VALUES
('resources/img/f1Global_Help.png'),
('resources/img/f2Health_and_Help.png'),
('resources/img/f3Free_Education.png'),
('resources/img/1A_CHILDS_HOPE_FOUNDATION.jpg'),
('resources/img/2CHICAGO_AREA_FAIR_HOUSING_ALLIANCE.png'),
('resources/img/3APPLIED_TECHNOLOGY_FOUNDATION.png'),
('resources/img/4CARE_FOR_LIFE_INC.png'),
('resources/img/5DAYS_FOR_GIRLS_INTERNATIONAL.png'),
('resources/img/6GUARDIAN_GROUP.jpg'),
('resources/img/7KAEME_FOUNDATION.jpg'),
('resources/img/8UNDEFEATEDD.png');

