package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;


import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public List<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    public Faculty getById(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty save(String name, String color) {
        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);
        return facultyRepository.save(faculty);
    }

    public Faculty deleteById(Long id) {
        facultyRepository.deleteById(id);
        return null;
    }

    public List<Faculty> filterFacultyByColor(String color) {
        return facultyRepository.findFacultyByColor(color);
    }
}
