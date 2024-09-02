package ru.hogwarts.school.controller;


import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collection;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty create(Faculty faculty) {
        return facultyService.add(faculty);
    }

    @PutMapping
    public Faculty editFaculty(Faculty faculty) {
        if (faculty.getId() == null) {
            throw new RuntimeException();
        }
        return facultyService.add(faculty);
    }

    @GetMapping
    public Collection<Faculty> getFaculties() {
        return facultyService.getFaculties();
    }

    @DeleteMapping("/{id}")
    public Faculty deleteFaculty(@PathVariable Long id) {
        return facultyService.remove(id);
    }

    @GetMapping("/{color}")
    public Collection<Faculty> findByColor(@PathVariable String color){
        return facultyService.getFacultiesByColor(color);
    }
}
