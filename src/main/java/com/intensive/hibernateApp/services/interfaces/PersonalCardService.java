package com.intensive.hibernateApp.services.interfaces;

import com.intensive.hibernateApp.controllers.dtos.personalCard.*;

import java.util.List;

public interface PersonalCardService {
    List<GetAllPersonalCardsDto> getAllPersonalCards();

    GetPersonalCardDto getPersonalCard(Long id);

    CreatePersonalCardDto createPersonalCard(CreatePersonalCardDto createPersonalCardDto, Long employeeId);

    UpdatePersonalCardDto updatePersonalCard(UpdatePersonalCardDto updatePersonalCardDto, Long id);

    DeletePersonalCardDto deletePersonalCard(Long id);
}
