package com.intensive.hibernateApp.services.implemetations;

import com.intensive.hibernateApp.controllers.dtos.department.*;
import com.intensive.hibernateApp.entities.Department;
import com.intensive.hibernateApp.exceptions.NotCorrectPropertiesException;
import com.intensive.hibernateApp.exceptions.ResourceAlreadyExistException;
import com.intensive.hibernateApp.exceptions.ResourceNotFoundException;
import com.intensive.hibernateApp.repositories.interfaces.DepartmentRepository;
import com.intensive.hibernateApp.services.interfaces.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<GetAllDepartmentsDto> getAllDepartments() {
        List<GetAllDepartmentsDto> departmentsListDto =departmentRepository.getAllDepartments()
            .stream().map(this::convertToGetAllDepartments).toList();

        log.info("All departments were returned");

        return departmentsListDto;
    }

    @Override
    public GetDepartmentDto getDepartment(Long id) {
        GetDepartmentDto departmentDto = convertToGetDepartment(getCurrentDepartment(id));

        log.info("Department with id {} was returned", id);

        return departmentDto;
    }

    @Override
    public CreateDepartmentDto createDepartment(CreateDepartmentDto createDepartmentDto) {
        if(createDepartmentDto.getName().isEmpty()) {
            throw new NotCorrectPropertiesException("Name can not be null");
        }
        else if (departmentRepository.getDepartmentByName(createDepartmentDto.getName())) {
            Department department = new Department();
            department.setName(createDepartmentDto.getName());

            CreateDepartmentDto departmentDto = convertToCreateDepartment(departmentRepository.createDepartment(department));

            log.info("Department with name {} was created", createDepartmentDto.getName());

            return departmentDto;
        } else {
            throw new ResourceAlreadyExistException("Department with name " + createDepartmentDto.getName() + " already exist");
        }
    }

    @Override
    public UpdateDepartmentDto updateDepartment(UpdateDepartmentDto updateDepartmentDto, Long id) {
        if(updateDepartmentDto.getName().isEmpty()) {
            throw new NotCorrectPropertiesException("Name can not be null");
        }
        else if (departmentRepository.getDepartmentByName(updateDepartmentDto.getName())) {
            Department department = getCurrentDepartment(id);
            department.setName(updateDepartmentDto.getName());

            Department departmentUpdated = departmentRepository.updateDepartment(department);
            UpdateDepartmentDto departmentDto = convertToUpdateDepartment(departmentUpdated);

            log.info("Department with id {} was updated", id);

            return departmentDto;
        }
        else {
            throw new ResourceAlreadyExistException("Department with name " + updateDepartmentDto.getName() + " already exist");
        }
    }

    @Override
    public DeleteDepartmentDto deleteDepartment(Long id) {
        Department department = getCurrentDepartment(id);

        Department departmentDeleted = departmentRepository.deleteDepartment(department);
        DeleteDepartmentDto departmentDto = convertToDeleteDepartment(departmentDeleted);

        log.info("Department with id {} was deleted", id);

        return departmentDto;
    }

    private Department getCurrentDepartment(Long id) {
        return departmentRepository.getDepartment(id)
            .orElseThrow(() -> new ResourceNotFoundException("Department with id " + id + " does not exist"));
    }

    private CreateDepartmentDto convertToCreateDepartment(Department department) {
        return modelMapper.map(department, CreateDepartmentDto.class);
    }

    private GetDepartmentDto convertToGetDepartment(Department department) {
        return modelMapper.map(department, GetDepartmentDto.class);
    }

    private GetAllDepartmentsDto convertToGetAllDepartments(Department department) {
        return modelMapper.map(department, GetAllDepartmentsDto.class);
    }

    private DeleteDepartmentDto convertToDeleteDepartment(Department department) {
        return modelMapper.map(department, DeleteDepartmentDto.class);
    }

    private UpdateDepartmentDto convertToUpdateDepartment(Department department) {
        return modelMapper.map(department, UpdateDepartmentDto.class);
    }
}
