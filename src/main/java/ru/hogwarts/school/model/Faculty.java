package ru.hogwarts.school.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity

public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String color;
    @OneToMany(mappedBy = "faculty")
    private Set<Student> students;

}
