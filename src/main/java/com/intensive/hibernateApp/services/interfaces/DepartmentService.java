package com.intensive.hibernateApp.services.interfaces;

import com.intensive.hibernateApp.controllers.dtos.department.*;

import java.util.List;
import java.util.Set;

public interface DepartmentService {
    List<GetAllDepartmentsDto> getAllDepartments();

    GetDepartmentDto getDepartment(Long id);

    CreateDepartmentDto createDepartment(CreateDepartmentDto createDepartmentDto);

    UpdateDepartmentDto updateDepartment(UpdateDepartmentDto updateDepartmentDto, Long id);

    DeleteDepartmentDto deleteDepartment(Long id);
}
