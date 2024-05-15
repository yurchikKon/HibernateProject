package com.intensive.hibernateApp.repositories.interfaces;

import com.intensive.hibernateApp.controllers.dtos.employee.CreateEmployeeDto;
import com.intensive.hibernateApp.controllers.dtos.employee.GetAllEmployeeDto;
import com.intensive.hibernateApp.controllers.dtos.employee.UpdateEmployeeDto;
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

    Employee updateEmployee(UpdateEmployeeDto updateEmployeeDto, Employee employee);

    Employee deleteEmployee(Employee employee);

    boolean getEmployeeByFullName(String firstName, String lastName);

    Set<Employee> getAllEmployeeByDepartment(Department department);

    Optional<PersonalCard> getEmployeePersonalCard(Employee employee);

    Set<Project> getAllProjectsByEmployee(Employee employee);
}
