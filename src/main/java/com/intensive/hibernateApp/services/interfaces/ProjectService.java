package com.intensive.hibernateApp.services.interfaces;

import com.intensive.hibernateApp.controllers.dtos.department.*;
import com.intensive.hibernateApp.controllers.dtos.project.*;

import java.util.List;
import java.util.Set;

public interface ProjectService {
    List<GetAllProjectsDto> getAllProjects();

    GetProjectDto getProject(Long id);

    CreateProjectDto createProject(CreateProjectDto createProjectDto);

    UpdateProjectDto updateProject(UpdateProjectDto updateProjectDto, Long id);

    DeleteProjectDto deleteProject(Long id);

    AddEmployeeToProjectDto addEmployeeToProject(Long employeeId, Long projectId);

    Set<GetAllEmployeesByProjectDto> getAllEmployeesByProject(Long id);
}
