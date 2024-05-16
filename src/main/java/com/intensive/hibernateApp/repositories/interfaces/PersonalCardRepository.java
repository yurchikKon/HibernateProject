package com.intensive.hibernateApp.repositories.interfaces;

import com.intensive.hibernateApp.controllers.dtos.employee.CreateEmployeeDto;
import com.intensive.hibernateApp.controllers.dtos.employee.UpdateEmployeeDto;
import com.intensive.hibernateApp.controllers.dtos.personalCard.CreatePersonalCardDto;
import com.intensive.hibernateApp.controllers.dtos.personalCard.UpdatePersonalCardDto;
import com.intensive.hibernateApp.entities.Department;
import com.intensive.hibernateApp.entities.Employee;
import com.intensive.hibernateApp.entities.PersonalCard;

import java.util.List;
import java.util.Optional;

public interface PersonalCardRepository {
    List<PersonalCard> getAllPersonalCards();

    Optional<PersonalCard> getPersonalCard(Long id);

    PersonalCard createPersonalCard(PersonalCard personalCard, Employee employee);

    PersonalCard updatePersonalCard(PersonalCard personalCard);

    PersonalCard deletePersonalCard(PersonalCard personalCard);
}
