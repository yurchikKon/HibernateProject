package com.intensive.hibernateApp.repositories.interfaces;

import com.intensive.hibernateApp.entities.Department;
import com.intensive.hibernateApp.entities.Employee;
import com.intensive.hibernateApp.entities.PersonalCard;
import com.intensive.hibernateApp.entities.Project;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeRepository {
    List<Employee> getAllEmployees();

    Optional<Employee> getEmployee(Long id);

    Employee createEmployee(Employee employee, Department department);

    Employee updateEmployee(Employee employee);

    Employee deleteEmployee(Employee employee);

    boolean checkIsEmployeeFullNameFree(String firstName, String lastName);

    Set<Employee> getAllEmployeeByDepartment(Long id);

    Optional<PersonalCard> getEmployeePersonalCard(Long id);

    Set<Project> getAllProjectsByEmployee(Long id);
}
