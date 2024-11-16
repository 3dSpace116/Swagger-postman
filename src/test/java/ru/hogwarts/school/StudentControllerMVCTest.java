
package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Arrays;

@WebMvcTest(StudentController.class)
public class StudentControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student(1L, "John Doe", 22, null);
    }

    @Test
    public void testCreateStudent() throws Exception {
        when(studentService.save(any(String.class), any(Integer.class))).thenReturn(student);

        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testEditStudent() throws Exception {
        when(studentService.getById(1L)).thenReturn(student);
        when(studentService.save(any(String.class), any(Integer.class))).thenReturn(student);

        mockMvc.perform(put("/student/1")
                        .param("name", "Jane Doe")
                        .param("email", "janedoe@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"));
    }

    @Test
    public void testGetAllStudents() throws Exception {
        when(studentService.getAll()).thenReturn(Arrays.asList(student));

        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        doNothing().when(studentService).deleteById(1L);

        mockMvc.perform(delete("/student/1"))
                .andExpect(status().isOk());

        verify(studentService, times(1)).deleteById(1L);
    }

    @Test
    public void testGetStudentById() throws Exception {
        when(studentService.getById(1L)).thenReturn(student);

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }
}