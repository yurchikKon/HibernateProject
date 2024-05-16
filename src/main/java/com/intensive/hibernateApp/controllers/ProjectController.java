package com.intensive.hibernateApp.controllers;

import com.intensive.hibernateApp.controllers.dtos.project.*;
import com.intensive.hibernateApp.services.interfaces.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/")
    public List<GetAllProjectsDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public GetProjectDto getProject(@PathVariable Long id) {
        return projectService.getProject(id);
    }

    @GetMapping("/{id}/employees")
    public Set<GetAllEmployeesByProjectDto> getAllEmployeesByProject(@PathVariable Long id) {
        return projectService.getAllEmployeesByProject(id);
    }

    @PostMapping("/")
    public CreateProjectDto createProject(@RequestBody CreateProjectDto createProjectDto) {
        return projectService.createProject(createProjectDto);
    }

    @PutMapping("/{id}")
    public UpdateProjectDto updateProject(@RequestBody UpdateProjectDto updateProjectDto,
        @PathVariable Long id) {
        return projectService.updateProject(updateProjectDto, id);
    }

    @PutMapping("/{projectId}/{employeeId}")
    public AddEmployeeToProjectDto addEmployeeToProject(@PathVariable Long projectId, @PathVariable Long employeeId) {
        return projectService.addEmployeeToProject(employeeId, projectId);
    }

    @DeleteMapping("/{id}")
    public DeleteProjectDto deleteProject(@PathVariable Long id) {
        return projectService.deleteProject(id);
    }
}
