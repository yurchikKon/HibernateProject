package com.intensive.hibernateApp.controllers.dtos.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEmployeePersonalCardDto {
    private int age;
    private String position;
    private int salary;
}
