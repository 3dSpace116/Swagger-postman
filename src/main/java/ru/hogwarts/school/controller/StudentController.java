package ru.hogwarts.school.controller;


import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(Student student) {
        return studentService.add(student);
    }

    @PutMapping
    public Student editStudent(Student student) {
        if (student.getId() == null) {
            throw new RuntimeException();
        }
        return studentService.add(student);
    }

    @GetMapping
    public Collection<Student> getStudents() {
        return studentService.getStudents();
    }

    @DeleteMapping("/{id}")
    public Student deleteStudent(@PathVariable Long id) {
        return studentService.remove(id);
    }

    @GetMapping("/{age}")
    public Collection<Student> findByAge(@PathVariable int age) {
        return studentService.getStudentsByAge(age);
    }

}
