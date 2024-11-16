SELECT s.name AS student_name, s.age, f.name AS faculty_name
FROM Student s
JOIN Faculty f ON s.faculty_id = f.id
WHERE f.name = 'Хогвартс';

SELECT s.name AS student_name, s.age
FROM Student s
WHERE s.avatar IS NOT NULL;