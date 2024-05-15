package com.intensive.hibernateApp.services.interfaces;

import com.intensive.hibernateApp.controllers.dtos.employee.*;

import java.util.List;
import java.util.Set;

public interface EmployeeService {
    List<GetAllEmployeeDto> getAllEmployees();

    GetEmployeeDto getEmployee(Long id);

    CreateEmployeeDto createEmployee(CreateEmployeeDto createEmployeeDto);

    UpdateEmployeeDto updateEmployee(UpdateEmployeeDto updateEmployeeDto, Long id);

    DeleteEmployeeDto deleteEmployee(Long id);

    Set<GetAllEmployeesByDepartmentDto> getAllEmployeesByDepartment(Long id);

    GetEmployeePersonalCardDto getEmployeePersonalCard(Long id);

    Set<GetAllProjectsByEmployeeDto> getAllProjectsByEmployee(Long id);
}
