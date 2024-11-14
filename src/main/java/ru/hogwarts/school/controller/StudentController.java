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
import java.util.Set;


@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final FacultyService facultyService;

    public StudentController(StudentService studentService, FacultyService facultyService) {
        this.studentService = studentService;
        this.facultyService = facultyService;
    }

    @PostMapping
    public Student create(String name, int age) {
        return studentService.save(name, age);
    }

    @PutMapping
    public Student editStudent(@PathVariable Long id, @RequestParam String name, @RequestParam int age) {
        if (studentService.getById(id) == null) {
            throw new RuntimeException();
        }
        return studentService.save(name,age);
    }

    @GetMapping
    public Collection<Student> getAll() {
        return studentService.getAll();
    }

    @GetMapping("/sortedNamesWhichBeginsA")
    public Collection<Student> sortedNamesWhichBeginsA(){return studentService.findStudentByName();}

    @GetMapping("/averageAge")
    public Integer averageAge(){return studentService.allStudentavgAge();}

    @DeleteMapping("/{id}")
    public Student deleteById(@PathVariable Long id) {
        return studentService.deleteById(id);
    }

    @GetMapping("/{age}")
    public Collection<Student> findStudentsByAge(@PathVariable int age) {
        return studentService.findStudentByAge(age);
    }

    @GetMapping("/filter")
    public List<Student> findStudentsByAgeBetween(@RequestParam(required = false) Integer age,
                                                  @RequestParam(required = false) Integer minAge,
                                                  @RequestParam(required = false) Integer maxAge) {
        if (age != null) {
            return studentService.findStudentByAge(age);
        }
        if (minAge != null && maxAge != null) {
            return studentService.findStudentsByAgeBetween(minAge, maxAge);

        }
        return null;
    }

    @GetMapping("student/{faculty_Id}")
    @Operation(summary = "Получение студента с факультета")
    public ResponseEntity<Set<Student>> getStudent(@PathVariable Long facultyId) {

        Faculty faculty = facultyService.getById(facultyId);
        Set<Student> students = faculty.getStudent();

        return ResponseEntity.ok(students);

    }

    @GetMapping ("/studentCount")
    public Integer studentCount (){return studentService.studentCount();}

    @GetMapping("/avgAge")
    public Integer avgAge(){return studentService.avgAge();}

    @GetMapping("/lastFiveId")
    public List<Student> lastFiveId() {return studentService.lastFiveId();}

}