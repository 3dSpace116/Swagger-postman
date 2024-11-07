package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;


import java.util.List;

@Service
public class FacultyService {
    private static final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public List<Faculty> getAll() {
        logger.info("Вызван запрос всех факультетов");
        return facultyRepository.findAll();
    }

    public Faculty getById(Long id) {
        logger.info("Вызван метод поиска факультета по id");
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty save(String name, String color) {
        logger.info("Вызван метод создания факультета по id");
        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);
        return facultyRepository.save(faculty);
    }

    public Faculty deleteById(Long id) {
        logger.info("Вызван метод удаления факультета по id");
        facultyRepository.deleteById(id);
        return null;
    }

    public List<Faculty> filterFacultyByColor(String color) {
        logger.info("Вызван метод поиска факультета по цвету");
        return facultyRepository.findFacultyByColor(color);
    }
}
