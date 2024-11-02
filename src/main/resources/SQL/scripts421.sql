SELECT TABLE Faculty (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    color VARCHAR(50),
    UNIQUE (name, color)
);


SELECT TABLE Student (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    age INT DEFAULT 20 CHECK (age >= 16),
    faculty_id INT,
    FOREIGN KEY (faculty_id) REFERENCES Faculty(id)
);