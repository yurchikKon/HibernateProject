package com.intensive.hibernateApp.controllers;

import com.intensive.hibernateApp.controllers.dtos.department.*;
import com.intensive.hibernateApp.services.interfaces.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/")
    public List<GetAllDepartmentsDto> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public GetDepartmentDto getDepartment(@PathVariable Long id){
        return departmentService.getDepartment(id);
    }

    @PostMapping("/")
    public CreateDepartmentDto createDepartment(@RequestBody CreateDepartmentDto createDepartmentDto) {
        return departmentService.createDepartment(createDepartmentDto);
    }

    @PutMapping("/{id}")
    public UpdateDepartmentDto updateDepartment(@RequestBody UpdateDepartmentDto updateDepartmentNameDto,
        @PathVariable Long id) {
        return departmentService.updateDepartment(updateDepartmentNameDto, id);
    }

    @DeleteMapping("/{id}")
    public DeleteDepartmentDto deleteDepartment(@PathVariable Long id) {
        return departmentService.deleteDepartment(id);
    }
}
