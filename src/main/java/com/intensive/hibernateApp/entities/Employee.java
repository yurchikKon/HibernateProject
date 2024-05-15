package com.intensive.hibernateApp.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employee")
@Setter
@Getter
@AllArgsConstructor
@ToString(exclude = "department")
@EqualsAndHashCode(exclude = "department")
@RequiredArgsConstructor
public class Employee implements Serializable {
    @Serial
    private static final long serialVersionUID = -5449326074498337967L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id_seq")
    @SequenceGenerator(name = "employee_id_seq", sequenceName = "sq_employee_id", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "personal_card_id")
    private PersonalCard personalCard;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "employee_project",
        joinColumns = {@JoinColumn(name = "employee_id")},
        inverseJoinColumns = {@JoinColumn(name = "project_id")}
    )
    private Set<Project> projects = new HashSet<>();
}
