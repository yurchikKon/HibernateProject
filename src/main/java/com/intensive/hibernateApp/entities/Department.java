package com.intensive.hibernateApp.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "department")
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_id_generator")
    @SequenceGenerator(name = "department_id_generator", sequenceName = "sq_department_id", allocationSize = 1)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department", cascade = {CascadeType.ALL})
    private Set<Employee> employees;
}
