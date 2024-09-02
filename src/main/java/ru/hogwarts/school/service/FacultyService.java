package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final Map<Long, Faculty> map = new HashMap<>();
    private static Long COUNTER = 0L;

    public Faculty add(Faculty faculty) {
        if (faculty.getId() == null) {
            faculty.setId(COUNTER++);
        }
        map.put(faculty.getId(), faculty);
        return faculty;
    }

    public Faculty remove(Long id) {
        return map.remove(id);
    }

    public Collection<Faculty> getFaculties() {
        return map.values();
    }

    public Faculty getById(Long id) {
        return map.get(id);
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        return map.values().stream().
                filter(it -> it.getColor() == color).
                collect(Collectors.toList());
    }
}
