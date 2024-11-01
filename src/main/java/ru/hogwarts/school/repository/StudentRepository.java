package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Student;

import java.util.List;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findStudentsByAge(int age);

    List<Student> findStudentsByAgeBetween(int minAge, int maxAge);

    @Query(value="SELECT COUNT(*) FROM student" ,nativeQuery = true)
    Integer studentCount();

    @Query(value="SELECT AVERAGE (age) FROM student", nativeQuery = true )
    Integer avgAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5",nativeQuery = true)
    List<Student> lastFiveId();
}