package com.intensive.hibernateApp.services.implemetations;

import com.intensive.hibernateApp.controllers.dtos.project.*;
import com.intensive.hibernateApp.entities.Employee;
import com.intensive.hibernateApp.entities.Project;
import com.intensive.hibernateApp.exceptions.NotCorrectPropertiesException;
import com.intensive.hibernateApp.exceptions.ResourceAlreadyExistException;
import com.intensive.hibernateApp.exceptions.ResourceNotFoundException;
import com.intensive.hibernateApp.repositories.interfaces.EmployeeRepository;
import com.intensive.hibernateApp.repositories.interfaces.ProjectRepository;
import com.intensive.hibernateApp.services.interfaces.ProjectService;
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
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<GetAllProjectsDto> getAllProjects() {
        List<GetAllProjectsDto> getAllProjectsDtoList = projectRepository.getAllProjects().stream()
            .map(this::convertToGetAllProjects).toList();

        log.info("All projects were returned");

        return getAllProjectsDtoList;
    }

    @Override
    public GetProjectDto getProject(Long id) {
        GetProjectDto getProjectDto = convertToGetProject(getCurrentProject(id));

        log.info("Project {} was returned", getProjectDto.getName());

        return getProjectDto;
    }

    @Override
    public CreateProjectDto createProject(CreateProjectDto createProjectDto) {
        if (createProjectDto.getName().isEmpty() || createProjectDto.getCompany().isEmpty()) {
            throw new NotCorrectPropertiesException("Name or company can not be empty");
        }
        else if (projectRepository.getProjectByName(createProjectDto.getName())) {
            Project project = new Project();
            project.setName(createProjectDto.getName());
            project.setCompany(createProjectDto.getCompany());

            CreateProjectDto projectDto = convertToCreateProject(projectRepository.createProject(project));

            log.info("Project {} was created", projectDto.getName());

            return projectDto;
        } else {
            throw new ResourceAlreadyExistException("Department with name " + createProjectDto.getName() + "already exist");
        }
    }

    @Override
    public UpdateProjectDto updateProject(UpdateProjectDto updateProjectDto, Long id) {
        if (updateProjectDto.getName().isEmpty()){
            throw new NotCorrectPropertiesException("Name or company can not be empty");
        }
        else if (projectRepository.getProjectByName(updateProjectDto.getName())) {
            Project project = getCurrentProject(id);
            project.setName(updateProjectDto.getName());

            Project projectUpdated = projectRepository.updateProject(project);
            UpdateProjectDto projectDto = convertToUpdateProject(projectUpdated);

            log.info("Project name was changed to {}", projectDto.getName());

            return projectDto;
        }
        else {
            throw new ResourceAlreadyExistException("Department with name " + updateProjectDto.getName() + "already exist");
        }
    }

    @Override
    public DeleteProjectDto deleteProject(Long id) {
        Project project = getCurrentProject(id);

        Project projectDeleted = projectRepository.deleteProject(project);
        DeleteProjectDto deleteProjectDto = convertToDeleteProject(projectDeleted);

        log.info("Project {} was deleted", deleteProjectDto.getName());

        return deleteProjectDto;
    }

    @Override
    public AddEmployeeToProjectDto addEmployeeToProject(Long employeeId, Long projectId) {
        Employee employee = getCurrentEmployee(employeeId);

        Project project = getCurrentProject(projectId);
        AddEmployeeToProjectDto projectDto = convertToAddEmployeeToProject(projectRepository.addEmployeeToProject(project, employee));

        log.info("User {} {} was added to project {}", employee.getLastName(), employee.getLastName(), projectDto.getName());

        return projectDto;
    }

    @Override
    public Set<GetAllEmployeesByProjectDto> getAllEmployeesByProject(Long id) {
        Project project = getCurrentProject(id);

        Set<GetAllEmployeesByProjectDto> projectDtoSet = projectRepository.getAllEmployeeByProject(project).stream()
            .map(this::convertToGetAllEmployeesByProjectDto).collect(Collectors.toSet());

        log.info("All employees by project {} were returned", project.getName());

        return projectDtoSet;
    }

    private Employee getCurrentEmployee(Long id) {
        return employeeRepository.getEmployee(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee with id " + id + " does not exist"));
    }

    private Project getCurrentProject(Long id) {
        return projectRepository.getProject(id)
            .orElseThrow(() -> new ResourceNotFoundException("Project with id " + id + " does not exist"));
    }

    private GetAllEmployeesByProjectDto convertToGetAllEmployeesByProjectDto(Employee employee) {
        return modelMapper.map(employee, GetAllEmployeesByProjectDto.class);
    }

    private AddEmployeeToProjectDto convertToAddEmployeeToProject(Project project) {
        return modelMapper.map(project, AddEmployeeToProjectDto.class);
    }

    private CreateProjectDto convertToCreateProject(Project project) {
        return modelMapper.map(project, CreateProjectDto.class);
    }

    private GetProjectDto convertToGetProject(Project project) {
        return modelMapper.map(project, GetProjectDto.class);
    }

    private GetAllProjectsDto convertToGetAllProjects(Project project) {
        return modelMapper.map(project, GetAllProjectsDto.class);
    }

    private DeleteProjectDto convertToDeleteProject(Project project) {
        return modelMapper.map(project, DeleteProjectDto.class);
    }

    private UpdateProjectDto convertToUpdateProject(Project project) {
        return modelMapper.map(project, UpdateProjectDto.class);
    }
}
