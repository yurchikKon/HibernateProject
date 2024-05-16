package com.intensive.hibernateApp.repositories.implementations;

import com.intensive.hibernateApp.entities.Department;
import com.intensive.hibernateApp.entities.Employee;
import com.intensive.hibernateApp.entities.PersonalCard;
import com.intensive.hibernateApp.entities.Project;
import com.intensive.hibernateApp.repositories.interfaces.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final SessionFactory sessionFactory;

    @Override
    public List<Employee> getAllEmployees() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select e from Employee e", Employee.class).getResultList();
        }
    }

    @Override
    public Optional<Employee> getEmployee(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Employee.class, id));
        }
    }

    @Override
    public Employee createEmployee(Employee employee, Department department) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Department departmentConnected = session.merge(department);
            employee.setDepartment(departmentConnected);
            session.persist(employee);
            departmentConnected.getEmployees().add(employee);
            session.merge(departmentConnected);

            transaction.commit();

            return employee;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Employee employeeNewLastName = session.merge(employee);
            transaction.commit();

            return employeeNewLastName;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public Employee deleteEmployee(Employee employee) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(employee);
            transaction.commit();

            return employee;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public boolean checkIsEmployeeFullNameFree(String firstName, String lastName) {
        try (Session session = sessionFactory.openSession()) {
            List<Employee> employeeList = session.createQuery("from Employee e where e.firstName = :firstName" +
                    " and e.lastName = :lastName", Employee.class)
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .getResultList();

            return employeeList.isEmpty();
        }
    }

    @Override
    public Set<Employee> getAllEmployeeByDepartment(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Department department = session.createQuery("select d from Department d join fetch" +
                    " d.employees where d.id = :id", Department.class)
                .setParameter("id", id).uniqueResult();

            return department.getEmployees();
        }
    }

    @Override
    public Optional<PersonalCard> getEmployeePersonalCard(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Employee employee = session.createQuery("select e from Employee e join fetch" +
                    " e.personalCard where e.id = :id", Employee.class)
                .setParameter("id", id).uniqueResult();

            return Optional.ofNullable(employee.getPersonalCard());
        }
    }

    @Override
    public Set<Project> getAllProjectsByEmployee(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Employee employee = session.createQuery("select e from Employee e join fetch e.projects where e.id = :id", Employee.class)
                .setParameter("id", id).uniqueResult();

            return employee.getProjects();
        }
    }
}
