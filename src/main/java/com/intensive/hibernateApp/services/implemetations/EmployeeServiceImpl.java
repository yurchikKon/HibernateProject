package com.intensive.hibernateApp.services.implemetations;

import com.intensive.hibernateApp.controllers.dtos.employee.*;
import com.intensive.hibernateApp.entities.Department;
import com.intensive.hibernateApp.entities.Employee;
import com.intensive.hibernateApp.entities.PersonalCard;
import com.intensive.hibernateApp.entities.Project;
import com.intensive.hibernateApp.exceptions.NotCorrectPropertiesException;
import com.intensive.hibernateApp.exceptions.ResourceAlreadyExistException;
import com.intensive.hibernateApp.exceptions.ResourceNotFoundException;
import com.intensive.hibernateApp.repositories.interfaces.DepartmentRepository;
import com.intensive.hibernateApp.repositories.interfaces.EmployeeRepository;
import com.intensive.hibernateApp.services.interfaces.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<GetAllEmployeeDto> getAllEmployees() {
        List<GetAllEmployeeDto> getAllEmployeeDtoList = employeeRepository.getAllEmployees().stream()
            .map(this::convertToGetAllEmployee).toList();

        log.info("All employees were returned");

        return getAllEmployeeDtoList;
    }

    @Override
    public GetEmployeeDto getEmployee(Long id) {
        return convertToGetEmployee(getCurrentEmployee(id));
    }

    @Override
    public CreateEmployeeDto createEmployee(CreateEmployeeDto createEmployeeDto) {
        if(createEmployeeDto.getLastName().isEmpty() || createEmployeeDto.getFirstName().isEmpty()) {
            throw new NotCorrectPropertiesException("First name or last name can not be null");
        }
        else if (employeeRepository.getEmployeeByFullName(createEmployeeDto.getFirstName(), createEmployeeDto.getLastName())) {
            Department department = departmentRepository.getDepartment(createEmployeeDto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department id " + createEmployeeDto.getDepartmentId()
                    + " does not exist"));

            Employee employee = new Employee();
            employee.setFirstName(createEmployeeDto.getFirstName());
            employee.setLastName(createEmployeeDto.getLastName());

            CreateEmployeeDto employeeDto = convertToCreateEmployee(employeeRepository.createEmployee(employee, department));

            log.info("Employee {} {} was created", employeeDto.getFirstName(), employeeDto.getLastName());

            return employeeDto;
        } else {
            throw new ResourceAlreadyExistException("Employee with first name " + createEmployeeDto.getFirstName()
                + " and last name " + createEmployeeDto.getLastName() + " already exist");
        }
    }

    @Override
    public UpdateEmployeeDto updateEmployee(UpdateEmployeeDto updateEmployeeDto, Long id) {
        if(updateEmployeeDto.getLastName().isEmpty()) {
            throw new NotCorrectPropertiesException("Last name can not be null");
        }
        Employee employee = getCurrentEmployee(id);
        employee.setLastName(updateEmployeeDto.getLastName());

        UpdateEmployeeDto employeeDto = convertToUpdateEmployee(employeeRepository.updateEmployee(employee));

        log.info("Employee last name was updated to {}", employeeDto.getLastName());

        return employeeDto;
    }

    @Override
    public DeleteEmployeeDto deleteEmployee(Long id) {
        Employee employee = getCurrentEmployee(id);

        DeleteEmployeeDto deleteEmployeeDto = convertToDeleteEmployee(employeeRepository.deleteEmployee(employee));

        log.info("Employee {} {} was deleted", deleteEmployeeDto.getFirstName(), deleteEmployeeDto.getLastName());

        return deleteEmployeeDto;
    }

    @Override
    public Set<GetAllEmployeesByDepartmentDto> getAllEmployeesByDepartment(Long id) {
        Department department = getCurrentDepartment(id);

        Set<GetAllEmployeesByDepartmentDto> employeesSetDto = employeeRepository.getAllEmployeeByDepartment(id).stream()
            .map(this::convertToGetAllEmployeesByDepartment).collect(Collectors.toSet());

        log.info("All employees by department {} were returned", department.getName());

        return employeesSetDto;
    }

    @Override
    public GetEmployeePersonalCardDto getEmployeePersonalCard(Long id) {
        Employee employee = getCurrentEmployee(id);

        PersonalCard personalCard = getCurrentPersonalCard(employee);
        GetEmployeePersonalCardDto personalCardDto = convertToGetEmployeePersonalCard(personalCard);

        log.info("Personal card of {} {} was returned", employee.getFirstName(), employee.getLastName());

        return personalCardDto;
    }

    @Override
    public Set<GetAllProjectsByEmployeeDto> getAllProjectsByEmployee(Long id) {
        Employee employee = getCurrentEmployee(id);

        Set<Project> projectSet = employeeRepository.getAllProjectsByEmployee(id);
        Set<GetAllProjectsByEmployeeDto> projectsSetDto = projectSet.stream()
            .map(this::convertToGetAllProjectsByEmployee).collect(Collectors.toSet());

        log.info("All projects by {} {} was returned", employee.getFirstName(), employee.getLastName());

        return projectsSetDto;
    }

    private PersonalCard getCurrentPersonalCard(Employee employee) {
        return employeeRepository.getEmployeePersonalCard(employee.getId())
            .orElseThrow(() -> new ResourceNotFoundException("Personal card of employee" +
                employee.getFirstName() + " " + employee.getLastName() + " does not exist"));
    }

    private Department getCurrentDepartment(Long id) {
        return departmentRepository.getDepartment(id)
            .orElseThrow(() -> new ResourceNotFoundException("Department with id " + " does not exist"));
    }

    private Employee getCurrentEmployee(Long id) {
        return employeeRepository.getEmployee(id).
            orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " does not exist"));
    }

    private GetAllProjectsByEmployeeDto convertToGetAllProjectsByEmployee(Project project) {
        return modelMapper.map(project, GetAllProjectsByEmployeeDto.class);
    }

    private GetEmployeePersonalCardDto convertToGetEmployeePersonalCard(PersonalCard personalCard) {
        return modelMapper.map(personalCard, GetEmployeePersonalCardDto.class);
    }

    private GetAllEmployeesByDepartmentDto convertToGetAllEmployeesByDepartment(Employee employee) {
        return modelMapper.map(employee, GetAllEmployeesByDepartmentDto.class);
    }

    private DeleteEmployeeDto convertToDeleteEmployee(Employee employee) {
        return modelMapper.map(employee, DeleteEmployeeDto.class);
    }

    private UpdateEmployeeDto convertToUpdateEmployee(Employee employee) {
        return modelMapper.map(employee, UpdateEmployeeDto.class);
    }

    private GetAllEmployeeDto convertToGetAllEmployee(Employee employee) {
        return modelMapper.map(employee, GetAllEmployeeDto.class);
    }

    private GetEmployeeDto convertToGetEmployee(Employee employee) {
        return modelMapper.map(employee, GetEmployeeDto.class);
    }

    private CreateEmployeeDto convertToCreateEmployee(Employee employee) {
        return modelMapper.map(employee, CreateEmployeeDto.class);
    }
}
