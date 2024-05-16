package com.intensive.hibernateApp.controllers.dtos.personalCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeletePersonalCardDto {
    private String male;
    private int age;
    private int salary;
    private String phoneNumber;
    private String currentAddress;
    private String position;
}
