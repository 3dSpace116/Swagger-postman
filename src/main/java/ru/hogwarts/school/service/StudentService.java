package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final Map<Long, Student> map = new HashMap<>();
    private static Long COUNTER = 0L;

    public Student add(Student student) {
        if (student.getId() == null) {
            student.setId(COUNTER++);
        }
        map.put(student.getId(), student);
        return student;
    }


    public Student remove(Long id) {
        return map.remove(id);
    }

    public Collection<Student> getStudents() {
        return map.values();
    }

    public Student getById(Long id) {
        return map.get(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        return map.values().stream().
                filter(it -> it.getAge() == age).
                collect(Collectors.toList());
    }
}
