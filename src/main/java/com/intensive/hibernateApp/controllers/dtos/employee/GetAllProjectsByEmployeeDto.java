package com.intensive.hibernateApp.controllers.dtos.employee;

import com.intensive.hibernateApp.entities.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAllProjectsByEmployeeDto {
    @NotEmpty(message = "Name can not be empty")
    private String name;
    @NotEmpty(message = "Company can not be empty")
    private String company;
}
