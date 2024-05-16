package com.intensive.hibernateApp.controllers.dtos.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetProjectDto {
    private String name;
    private String company;
}
