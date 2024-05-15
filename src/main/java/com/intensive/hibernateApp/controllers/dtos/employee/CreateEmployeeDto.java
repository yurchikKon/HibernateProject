package com.intensive.hibernateApp.controllers.dtos.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeDto {
    private String firstName;
    private String lastName;
    private Long departmentId;
}
