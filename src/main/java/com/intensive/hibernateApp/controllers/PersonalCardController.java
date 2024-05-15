package com.intensive.hibernateApp.controllers;

import com.intensive.hibernateApp.controllers.dtos.personalCard.*;
import com.intensive.hibernateApp.controllers.dtos.project.CreateProjectDto;
import com.intensive.hibernateApp.controllers.dtos.project.UpdateProjectDto;
import com.intensive.hibernateApp.services.interfaces.PersonalCardService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/personalCards")
@RequiredArgsConstructor
public class PersonalCardController {
    private final PersonalCardService personalCardService;

    @GetMapping("/")
    public List<GetAllPersonalCardsDto> getAllPersonalCards(){
        return personalCardService.getAllPersonalCards();
    }

    @GetMapping("/{id}")
    public GetPersonalCardDto getPersonalCard(@PathVariable Long id){
        return personalCardService.getPersonalCard(id);
    }

    @PostMapping("/{employeeId}")
    public CreatePersonalCardDto createPersonalCard(@RequestBody CreatePersonalCardDto createPersonalCardDto,
        @PathVariable Long employeeId) {
        return personalCardService.createPersonalCard(createPersonalCardDto, employeeId);
    }

    @PutMapping("/{id}")
    public UpdatePersonalCardDto updatePersonalCard(@RequestBody UpdatePersonalCardDto updatePersonalCardDto,
        @PathVariable Long id) {
        return personalCardService.updatePersonalCard(updatePersonalCardDto, id);
    }

    @DeleteMapping("/{id}")
    public DeletePersonalCardDto deletePersonalCard(@PathVariable Long id) {
        return personalCardService.deletePersonalCard(id);
    }
}
