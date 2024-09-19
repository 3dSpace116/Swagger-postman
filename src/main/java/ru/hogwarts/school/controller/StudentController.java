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

}
