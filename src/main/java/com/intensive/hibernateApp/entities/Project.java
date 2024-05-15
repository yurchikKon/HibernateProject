package com.intensive.hibernateApp.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project")
@Setter
@Getter
@ToString(exclude = "employees")
@EqualsAndHashCode(exclude = "employees")
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_id_generator")
    @SequenceGenerator(name = "project_id_generator", sequenceName = "sq_project_id", allocationSize = 1)
    private Long id;

    private String name;
    private String company;

    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy = "projects")
    private Set<Employee> employees = new HashSet<>();
}
