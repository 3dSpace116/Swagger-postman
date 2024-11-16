package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


import java.util.Collection;
import java.util.List;

@Service
public class StudentService {
    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAll() {
        logger.info("Вызван метод поиска всех студентов");
        return studentRepository.findAll();
    }

    public Student getById(Long id) {
        logger.info("Вызван метод поиска студента по id");
        return studentRepository.findById(id).orElse(null);
    }

    public Student save(String name, int age) {
        logger.info("Вызван метод создания студента");
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        return studentRepository.save(student);
    }

    public Student deleteById(Long id) {
        logger.info("Вызван метод удаления студента по id");
        studentRepository.deleteById(id);
        return null;
    }

    public List<Student> findStudentByAge(int age) {
        logger.info("Вызван метод поиска студентов по возрасту");
        return studentRepository.findStudentsByAge(age);
    }

    public List<Student> findStudentsByAgeBetween(int minAge, int maxAge) {
        logger.info("Вызван метод поиска студентов по диапазону возрастов");
        return studentRepository.findStudentsByAgeBetween(minAge, maxAge);
    }

    public Integer studentCount() {
        logger.info("Вызван метод вывода количества студентов");
        return studentRepository.studentCount();
    }

    public Integer avgAge() {
        logger.info("Вызван метод вывода среднего возраста студентов");
        return studentRepository.avgAge();
    }

    public List<Student> lastFiveId() {
        logger.info("Вызван метод вывода пяти последних студентов");
        return studentRepository.lastFiveId();
    }


    public Collection<Student> findStudentByName() {
        return studentRepository.findAll().stream().
                filter(name -> name.startsWith("А")).
                map(String::toUpperCase).
                sorted().
                collect(Collectors.toList());
    }

    public Integer allStudentavgAge() {
        return (int) studentRepository.findAll().stream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0.0);
    }

    public void printStudentsInParallel() {
        System.out.println(getById(0L));
        System.out.println(getById(1L));
        Thread thread1 = new Thread(() -> {
            System.out.println(getById(2L));
            System.out.println(getById(3L));
        });
        Thread thread2 = new Thread(() -> {
            System.out.println(getById(4L));
            System.out.println(getById(5L));
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Ошибка при ожидании завершения потоков: " + e.getMessage());
        }
    }

    public void printStudentsSynchronized() {

        printStudent(getById(0L).getName());
        printStudent(getById(1L).getName());
        Thread thread1 = new Thread(() -> {
            printStudent(getById(2L).getName());
            printStudent(getById(3L).getName());
        });
        Thread thread2 = new Thread(() -> {
            printStudent(getById(4L).getName());
            printStudent(getById(5L).getName());
        });
        thread1.start();
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Ошибка при ожидании завершения потоков: " + e.getMessage());
    }


}
    private synchronized void printStudent(String name) {
        System.out.println(name);
    }
}
