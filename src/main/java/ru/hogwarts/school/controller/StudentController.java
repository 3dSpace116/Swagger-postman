package ru.hogwarts.school.controller;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;

import static com.sun.beans.introspect.PropertyInfo.Name.required;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(String name, int age) {
        return studentService.save(name, age);
    }

    @PutMapping
    public Student editStudent(Student student) {
        if (student.getId() == null) {
            throw new RuntimeException();
        }
        return studentService.save(student);
    }

    @GetMapping
    public Collection<Student> getAll() {
        return studentService.getAll();
    }

    @DeleteMapping("/{id}")
    public Student deleteById(@PathVariable Long id) {
        return studentService.deleteById(id);
    }

    @GetMapping("/{age}")
    public Collection<Student> findStudentsByAge(@PathVariable int age) {
        return studentService.findStudentsByAge(age);
    }

    @GetMapping {
        "/filter"
    }

    public List<Student> findStudentsByAgeBetween(@RequestParam(required = false)Integer age,
    @RequestParam(required=false)Integer minAge,
    @RequestParam(required=false)Integer maxAge)

    {
        if (age != null) {
            return studentService.findStudentsByAge(age);
        }
        if (minAge != null && maxAge != null) {
            return studentService.findStudentsByAgeBetween(minAge, maxAge);

        }
    }
    @GetMapping("student/{faculty_Id}")
    @Operation(summary = "Получение студента с факультета")
    public ResponseEntity<Faculty> getStudent(@PathVariable Long facultyId) {

        Student student = FacultyService.getById(facultyId).getFaculty();

        return ResponseEntity.ok(student);

    }
}