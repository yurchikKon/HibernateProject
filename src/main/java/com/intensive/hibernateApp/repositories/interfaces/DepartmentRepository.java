package com.intensive.hibernateApp.repositories.interfaces;

import com.intensive.hibernateApp.controllers.dtos.department.CreateDepartmentDto;
import com.intensive.hibernateApp.controllers.dtos.department.UpdateDepartmentDto;
import com.intensive.hibernateApp.entities.Department;
import com.intensive.hibernateApp.entities.Employee;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DepartmentRepository {
    List<Department> getAllDepartments();

    Optional<Department> getDepartment(Long id);

    Department createDepartment(Department department);

    Department updateDepartment(Department department);

    Department deleteDepartment(Department department);

    boolean getDepartmentByName(String name);
}
