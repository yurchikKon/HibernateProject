package com.intensive.hibernateApp.controllers.dtos.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEmployeeDto {
    private String firstName;
    private String lastName;
}
