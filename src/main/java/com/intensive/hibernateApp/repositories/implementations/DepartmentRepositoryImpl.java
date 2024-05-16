package com.intensive.hibernateApp.repositories.implementations;

import com.intensive.hibernateApp.controllers.dtos.department.CreateDepartmentDto;
import com.intensive.hibernateApp.controllers.dtos.department.UpdateDepartmentDto;
import com.intensive.hibernateApp.entities.Department;
import com.intensive.hibernateApp.entities.Employee;
import com.intensive.hibernateApp.exceptions.ResourceNotFoundException;
import com.intensive.hibernateApp.repositories.interfaces.DepartmentRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class DepartmentRepositoryImpl implements DepartmentRepository {
    private final SessionFactory sessionFactory;
    @Override
    public List<Department> getAllDepartments() {
        Session session = sessionFactory.openSession();
        List<Department> departmentList = session.createQuery("select d from Department d", Department.class).getResultList();
        session.close();

        return departmentList;
    }

    @Override
    public Optional<Department> getDepartment(Long id) {
        Session session = sessionFactory.openSession();;
        Department department = session.get(Department.class, id);
        session.close();

        return Optional.ofNullable(department);
    }

    @Override
    public Department createDepartment(Department department) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(department);
            session.getTransaction().commit();
            session.close();

            return department;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public Department updateDepartment(Department department) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Department departmentUpdated = session.merge(department);
            session.getTransaction().commit();
            session.close();

            return departmentUpdated;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public Department deleteDepartment(Department department) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.remove(department);
            session.getTransaction().commit();
            session.close();

            return department;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public boolean getDepartmentByName(String name) {
        Session session = sessionFactory.openSession();
        List<Department> departmentList = session.createQuery("from Department d where d.name = :name", Department.class)
            .setParameter("name", name)
            .getResultList();
        session.close();

        return departmentList.isEmpty();
    }
}
