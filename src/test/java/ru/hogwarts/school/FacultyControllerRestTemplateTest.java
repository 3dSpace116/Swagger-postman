package ru.hogwarts.school;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hogwarts.school.model.Faculty;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc
public class FacultyControllerRestTemplateTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String BASE_URL = "/faculty";

    @BeforeEach
    public void setUp() {

    }


    @Test
    public void testCreateFaculty() {
        Faculty faculty = new Faculty();
        faculty.setName("Science");
        faculty.setColor("Green");

        ResponseEntity<Faculty> response = restTemplate.postForEntity(BASE_URL, faculty, Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Science");
    }

    @Test
    public void testEditFaculty() {

        Long facultyId = 1L;
        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setName("Arts");
        updatedFaculty.setColor("Blue");

        restTemplate.put(BASE_URL + "/" + facultyId, updatedFaculty);

        ResponseEntity<Faculty> response = restTemplate.getForEntity(BASE_URL + "/" + facultyId, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody().getName()).isEqualTo("Arts");
    }

    @Test
    public void testGetAllFaculties() {
        ResponseEntity<List> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, null, List.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void testDeleteFaculty() {

        Long facultyId = 2L;

        restTemplate.delete(BASE_URL + "/" + facultyId);

        ResponseEntity<Faculty> response = restTemplate.getForEntity(BASE_URL + "/" + facultyId, Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);
    }

    @Test
    public void testFilterFacultiesByColor() {
        String color = "Green";

        ResponseEntity<List> response = restTemplate.exchange(BASE_URL + "?color=" + color, HttpMethod.GET, null, List.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    public void testGetFacultyByStudentId() {

        Long studentId = 3L;

        ResponseEntity<Faculty> response = restTemplate.getForEntity(BASE_URL + "/faculty/" + studentId, Faculty.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isNotNull();
    }
}