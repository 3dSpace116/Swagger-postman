package ru.hogwarts.school.controller;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;
    private final StudentService studentService;

    public FacultyController(FacultyService facultyService, StudentService studentService) {
        this.facultyService = facultyService;
        this.studentService = studentService;
    }

    @PostMapping
    public Faculty create(String name, String color) {
        return facultyService.save(name, color);
    }

    @PutMapping("/{id}")
    public Faculty editFaculty(@PathVariable Long id, @RequestParam String name, @RequestParam String color) {
        if (facultyService.getById(id) == null) {
            throw new RuntimeException();
        }
        return facultyService.save(name, color);
    }

    @GetMapping
    public Collection<Faculty> getAll() {
        return facultyService.getAll();
    }

    @DeleteMapping("/{id}")
    public Faculty deleteFaculty(@PathVariable Long id) {
        return facultyService.deleteById(id);
    }

    @GetMapping("/{color}")
    public Collection<Faculty> filterFacultiesByColor(@PathVariable String color) {
        return facultyService.filterFacultyByColor(color);
    }


    @GetMapping("faculty/{studentId}")
    @Operation(summary = "Получение факультета студента")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long studentId) {
        Faculty faculty = studentService.getById(studentId).getFaculty();
        return ResponseEntity.ok(faculty);

    }
}
