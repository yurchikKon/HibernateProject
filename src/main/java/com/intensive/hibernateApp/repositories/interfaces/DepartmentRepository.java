package com.intensive.hibernateApp.repositories.interfaces;

import com.intensive.hibernateApp.entities.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {
    List<Department> getAllDepartments();

    Optional<Department> getDepartment(Long id);

    Department createDepartment(Department department);

    Department updateDepartment(Department department);

    Department deleteDepartment(Department department);

    boolean checkIsDepartmentNameFree(String name);
}
