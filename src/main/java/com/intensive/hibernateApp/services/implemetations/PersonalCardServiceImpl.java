package com.intensive.hibernateApp.services.implemetations;

import com.intensive.hibernateApp.controllers.dtos.personalCard.*;
import com.intensive.hibernateApp.entities.Employee;
import com.intensive.hibernateApp.entities.PersonalCard;
import com.intensive.hibernateApp.exceptions.NotCorrectPropertiesException;
import com.intensive.hibernateApp.repositories.interfaces.EmployeeRepository;
import com.intensive.hibernateApp.repositories.interfaces.PersonalCardRepository;
import com.intensive.hibernateApp.services.interfaces.PersonalCardService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonalCardServiceImpl implements PersonalCardService {
    private final PersonalCardRepository personalCardRepository;
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<GetAllPersonalCardsDto> getAllPersonalCards() {
        List<GetAllPersonalCardsDto> personalCardsListDto = personalCardRepository.getAllPersonalCards()
            .stream().map(this::convertToGetAllPersonalCards).toList();

        log.info("All personal cards were returned");

        return personalCardsListDto;
    }

    @Override
    public GetPersonalCardDto getPersonalCard(Long id) {
        GetPersonalCardDto personalCardDto = convertToGetPersonalCard(getCurrentPersonalCard(id));

        log.info("Personal card with id {} was returned", id);

        return personalCardDto;
    }

    @Override
    public CreatePersonalCardDto createPersonalCard(CreatePersonalCardDto createPersonalCardDto, Long employeeId) {
        if (createPersonalCardDto.getPosition().isEmpty() || createPersonalCardDto.getPhoneNumber().isEmpty()) {
            throw new NotCorrectPropertiesException("Position and phone number can not be empty");
        }
        else {
            Employee employee = employeeRepository.getEmployee(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee with id " + employeeId + " does not exist"));

            PersonalCard personalCard = convertFromCreatePersonalCardDto(createPersonalCardDto);

            employee.setPersonalCard(personalCard);

            PersonalCard personalCardCreated = personalCardRepository.createPersonalCard(personalCard, employee);
            CreatePersonalCardDto personalCardDto = convertToCreatePersonalCard(personalCardCreated);

            log.info("Personal card for employee with id {} was created", employeeId);

            return personalCardDto;
        }
    }

    @Override
    public UpdatePersonalCardDto updatePersonalCard(UpdatePersonalCardDto updatePersonalCardDto, Long id) {
        PersonalCard personalCard = getCurrentPersonalCard(id);
        personalCard.setSalary(updatePersonalCardDto.getSalary());

        PersonalCard personalCardUpdated = personalCardRepository.updatePersonalCard(personalCard);
        UpdatePersonalCardDto personalCardDto = convertToUpdatePersonalCard(personalCardUpdated);

        log.info("Personal card with id {} was updated", id);

        return personalCardDto;
    }

    @Override
    public DeletePersonalCardDto deletePersonalCard(Long id) {
        PersonalCard personalCard = getCurrentPersonalCard(id);

        PersonalCard personalCardDeleted = personalCardRepository.deletePersonalCard(personalCard);
        DeletePersonalCardDto personalCardDto = convertToDeletePersonalCard(personalCardDeleted);

        log.info("Personal card with id {} was deleted", id);

        return personalCardDto;
    }

    private PersonalCard getCurrentPersonalCard(Long id) {
        return personalCardRepository.getPersonalCard(id)
            .orElseThrow(() -> new EntityNotFoundException("Personal card with id " + id + " does not exist"));
    }

    private PersonalCard convertFromCreatePersonalCardDto(CreatePersonalCardDto createPersonalCardDto) {
        return modelMapper.map(createPersonalCardDto, PersonalCard.class);
    }

    private CreatePersonalCardDto convertToCreatePersonalCard(PersonalCard personalCard) {
        return modelMapper.map(personalCard, CreatePersonalCardDto.class);
    }

    private GetPersonalCardDto convertToGetPersonalCard(PersonalCard personalCard) {
        return modelMapper.map(personalCard, GetPersonalCardDto.class);
    }

    private GetAllPersonalCardsDto convertToGetAllPersonalCards(PersonalCard personalCard) {
        return modelMapper.map(personalCard, GetAllPersonalCardsDto.class);
    }

    private DeletePersonalCardDto convertToDeletePersonalCard(PersonalCard personalCard) {
        return modelMapper.map(personalCard, DeletePersonalCardDto.class);
    }

    private UpdatePersonalCardDto convertToUpdatePersonalCard(PersonalCard personalCard) {
        return modelMapper.map(personalCard, UpdatePersonalCardDto.class);
    }

}
