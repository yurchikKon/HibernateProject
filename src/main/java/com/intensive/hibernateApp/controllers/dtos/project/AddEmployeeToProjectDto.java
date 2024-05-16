package com.intensive.hibernateApp.controllers.dtos.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddEmployeeToProjectDto {
    private String name;
    private String company;
}
