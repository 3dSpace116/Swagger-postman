package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private List<Student> studentList;

    @BeforeEach
    public void setUp() {

        studentList = new ArrayList<>();
        studentList.add(new Student(1L, "John Doe", 20, null));
        studentList.add(new Student(2L, "Jane Doe", 22, null));
    }

    @LocalServerPort
    private int port;

    @Test
    public void testCreateStudent() {
        Student newStudent = new Student();
        newStudent.setName("Alice Smith");
        newStudent.setAge(21);
        ResponseEntity<Student> response = restTemplate.postForEntity("http://localhost:" + port +
                "/student?name=Alice Smith&age=21", newStudent, Student.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Alice Smith");
    }

    @Test
    public void testEditStudent() {

        Student updatedStudent = new Student();
        updatedStudent.setName("John Smith");
        updatedStudent.setAge(23);

        ResponseEntity<Student> response = restTemplate.postForEntity("http://localhost:" + port +
                "/student?id=1&name=John Smith&age=23", updatedStudent, Student.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("John Smith");
    }

    @Test
    public void testGetAllStudents() {

        ResponseEntity<Collection> response = restTemplate.getForEntity("http://localhost:" + port +"/student",+"/getAll", Collection.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void testDeleteStudentById() {

        ResponseEntity<Void> response = restTemplate.exchange("http://localhost:" + port +"/student", HttpMethod.DELETE, null, Void.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        ResponseEntity<Collection> allStudentsResponse = restTemplate.getForEntity("/student", Collection.class);
        assertThat(allStudentsResponse.getBody()).hasSize(1);
    }

    @Test
    public void testFindStudentsByAge() {

        ResponseEntity<Collection> response = restTemplate.getForEntity("/student" + "/20", Collection.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void testFindStudentsByAgeBetween() {

        ResponseEntity<Collection> response = restTemplate.getForEntity("/student" + "/filter?minAge=18&maxAge=21", Collection.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        Collection<?> studentsInRange = response.getBody();
        assertThat(studentsInRange).hasSize(1);
    }

    @Test
    public void testGetStudentsByFacultyId() {
        long facultyId = 1L;

        ResponseEntity<Set> response = restTemplate.getForEntity("/student" + "/student/" + facultyId, Set.class);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotEmpty();
    }
}