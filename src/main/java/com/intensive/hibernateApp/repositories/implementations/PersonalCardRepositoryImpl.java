package com.intensive.hibernateApp.repositories.implementations;

import com.intensive.hibernateApp.controllers.dtos.personalCard.CreatePersonalCardDto;
import com.intensive.hibernateApp.controllers.dtos.personalCard.UpdatePersonalCardDto;
import com.intensive.hibernateApp.entities.Department;
import com.intensive.hibernateApp.entities.Employee;
import com.intensive.hibernateApp.entities.PersonalCard;
import com.intensive.hibernateApp.repositories.interfaces.PersonalCardRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PersonalCardRepositoryImpl implements PersonalCardRepository {
    private final SessionFactory sessionFactory;

    @Override
    public List<PersonalCard> getAllPersonalCards() {
        Session session = sessionFactory.openSession();
        List<PersonalCard> personalCardList = session.createQuery("select pc from PersonalCard pc", PersonalCard.class).getResultList();
        session.close();

        return personalCardList;
    }

    @Override
    public Optional<PersonalCard> getPersonalCard(Long id) {
        Session session = sessionFactory.openSession();;

        PersonalCard personalCard = session.get(PersonalCard.class, id);
        session.close();

        return Optional.ofNullable(personalCard);
    }

    @Override
    public PersonalCard createPersonalCard(PersonalCard personalCard, Employee employee) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(personalCard);
        session.merge(employee);
        session.getTransaction().commit();
        session.close();

        return personalCard;
    }

    @Override
    public PersonalCard updatePersonalCard(PersonalCard personalCard) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        PersonalCard personalCardUpdated = session.merge(personalCard);
        session.getTransaction().commit();
        session.close();

        return personalCardUpdated;
    }

    @Override
    public PersonalCard deletePersonalCard(PersonalCard personalCard) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(personalCard);
        session.getTransaction().commit();
        session.close();

        return personalCard;
    }
}
