
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

import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Arrays;
import java.util.Collections;

@WebMvcTest(FacultyController.class)
public class FacultyControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FacultyService facultyService;

    @Autowired
    private ObjectMapper objectMapper;

    private Faculty faculty;

    @BeforeEach
    public void setUp() {
        faculty = new Faculty(1L, "Science", "Green", null);
    }

    @Test
    public void testCreateFaculty() throws Exception {
        when(facultyService.save(any(String.class), any(String.class))).thenReturn(faculty);

        mockMvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Science"))
                .andExpect(jsonPath("$.color").value("Green"));
    }

    @Test
    public void testEditFaculty() throws Exception {
        when(facultyService.getById(1L)).thenReturn(faculty);
        when(facultyService.save(any(String.class), any(String.class))).thenReturn(faculty);

        mockMvc.perform(put("/faculty/1")
                        .param("name", "Arts")
                        .param("color", "Blue"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Arts"))
                .andExpect(jsonPath("$.color").value("Blue"));
    }

    @Test
    public void testGetAllFaculties() throws Exception {
        when(facultyService.getAll()).thenReturn(Arrays.asList(faculty));

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Science"));
    }

    @Test
    public void testDeleteFaculty() throws Exception {
        doNothing().when(facultyService).deleteById(1L);

        mockMvc.perform(delete("/faculty/1"))
                .andExpect(status().isOk());

        verify(facultyService, times(1)).deleteById(1L);
    }

    @Test
    public void testFilterFacultiesByColor() throws Exception {
        when(facultyService.filterFacultyByColor("Green")).thenReturn(Collections.singletonList(faculty));

        mockMvc.perform(get("/faculty/Green"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value("Green"));
    }

    @Test
    public void testGetFacultyByStudentId() throws Exception {
        // Предполагаем, что у студента с id=3 есть факультет "Science"
        Long studentId = 3L;
        when(facultyService.getById(studentId)).thenReturn(faculty);

        mockMvc.perform(get("/faculty/faculty/" + studentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Science"));
    }
}