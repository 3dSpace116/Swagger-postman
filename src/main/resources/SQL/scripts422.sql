CREATE TABLE Car (
    id INT PRIMARY KEY,
    brand VARCHAR(100),
    model VARCHAR(100),
    price DECIMAL(10, 2)
);

CREATE TABLE Person (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT CHECK (age >= 0),
    has_license BOOLEAN DEFAULT FALSE,
    car_id INT,
    FOREIGN KEY (car_id) REFERENCES Car(id)
);