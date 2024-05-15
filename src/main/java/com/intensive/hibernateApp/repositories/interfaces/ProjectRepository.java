package com.intensive.hibernateApp.repositories.interfaces;

import com.intensive.hibernateApp.entities.Employee;
import com.intensive.hibernateApp.entities.Project;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProjectRepository {
    List<Project> getAllProjects();

    Optional<Project> getProject(Long id);

    Project createProject(Project project);

    Project updateProject(Project project);

    Project deleteProject(Project project);

    boolean getProjectByName(String name);

    Project addEmployeeToProject(Project project, Employee employee);

    Set<Employee> getAllEmployeeByProject(Project project);
}
