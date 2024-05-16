package com.intensive.hibernateApp.controllers;

import com.intensive.hibernateApp.controllers.dtos.employee.*;
import com.intensive.hibernateApp.services.interfaces.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RequestMapping("/employees")
@RequiredArgsConstructor
@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/")
    public List<GetAllEmployeeDto> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public GetEmployeeDto getEmployee(@PathVariable Long id){
        return employeeService.getEmployee(id);
    }

    @GetMapping("/department/{departmentId}")
    public Set<GetAllEmployeesByDepartmentDto> getAllEmployeesByDepartment(@PathVariable Long departmentId) {
        return employeeService.getAllEmployeesByDepartment(departmentId);
    }
    @GetMapping("/{id}/personalCard")
    public GetEmployeePersonalCardDto getEmployeePersonalCard(@PathVariable Long id) {
        return employeeService.getEmployeePersonalCard(id);
    }

    @GetMapping("/{id}/projects")
    public Set<GetAllProjectsByEmployeeDto> getAllProjectsByEmployee(@PathVariable Long id) {
        return employeeService.getAllProjectsByEmployee(id);
    }

    @PostMapping("/")
    public CreateEmployeeDto createEmployee(@RequestBody CreateEmployeeDto createEmployeeDto) {
        return employeeService.createEmployee(createEmployeeDto);
    }

    @PutMapping("/{id}")
    public UpdateEmployeeDto updateEmployee(@RequestBody UpdateEmployeeDto updateEmployeeLastNameDto,
        @PathVariable Long id) {
        return employeeService.updateEmployee(updateEmployeeLastNameDto, id);
    }

    @DeleteMapping("/{id}")
    public DeleteEmployeeDto deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }
}
