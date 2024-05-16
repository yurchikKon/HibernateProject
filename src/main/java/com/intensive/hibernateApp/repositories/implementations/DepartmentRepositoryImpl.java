package com.intensive.hibernateApp.repositories.implementations;

import com.intensive.hibernateApp.entities.Department;
import com.intensive.hibernateApp.repositories.interfaces.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class DepartmentRepositoryImpl implements DepartmentRepository {
    private final SessionFactory sessionFactory;

    @Override
    public List<Department> getAllDepartments() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select d from Department d", Department.class).getResultList();
        }
    }

    @Override
    public Optional<Department> getDepartment(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Department.class, id));
        }
    }

    @Override
    public Department createDepartment(Department department) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(department);
            transaction.commit();

            return department;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public Department updateDepartment(Department department) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Department departmentUpdated = session.merge(department);
            transaction.commit();

            return departmentUpdated;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public Department deleteDepartment(Department department) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(department);
            transaction.commit();

            return department;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public boolean checkIsDepartmentNameFree(String name) {
        try (Session session = sessionFactory.openSession()) {
            List<Department> departmentList = session.createQuery("from Department d where d.name = :name", Department.class)
                .setParameter("name", name)
                .getResultList();

            return departmentList.isEmpty();
        }
    }
}
