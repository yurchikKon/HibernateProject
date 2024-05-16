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
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PersonalCardRepositoryImpl implements PersonalCardRepository {
    private final SessionFactory sessionFactory;

    @Override
    public List<PersonalCard> getAllPersonalCards() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select pc from PersonalCard pc", PersonalCard.class).getResultList();
        }
    }

    @Override
    public Optional<PersonalCard> getPersonalCard(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(PersonalCard.class, id));
        }
    }

    @Override
    public PersonalCard createPersonalCard(PersonalCard personalCard, Employee employee) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(personalCard);
            session.merge(employee);
            transaction.commit();

            return personalCard;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public PersonalCard updatePersonalCard(PersonalCard personalCard) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession();) {
            transaction = session.beginTransaction();
            PersonalCard personalCardUpdated = session.merge(personalCard);
            transaction.commit();

            return personalCardUpdated;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public PersonalCard deletePersonalCard(PersonalCard personalCard) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(personalCard);
            transaction.commit();

            return personalCard;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
