package com.intensive.hibernateApp.controllers.dtos.personalCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePersonalCardDto {
    private String male;
    private Integer age;
    private Integer salary;
    private String phoneNumber;
    private String currentAddress;
    private String position;
}
