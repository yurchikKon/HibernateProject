package com.intensive.hibernateApp.repositories.implementations;

import com.intensive.hibernateApp.controllers.dtos.employee.CreateEmployeeDto;
import com.intensive.hibernateApp.controllers.dtos.employee.UpdateEmployeeDto;
import com.intensive.hibernateApp.entities.Department;
import com.intensive.hibernateApp.entities.Employee;
import com.intensive.hibernateApp.entities.PersonalCard;
import com.intensive.hibernateApp.entities.Project;
import com.intensive.hibernateApp.exceptions.ResourceNotFoundException;
import com.intensive.hibernateApp.repositories.interfaces.DepartmentRepository;
import com.intensive.hibernateApp.repositories.interfaces.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {
    private final SessionFactory sessionFactory;

    @Override
    public List<Employee> getAllEmployees() {
        Session session = sessionFactory.openSession();
        List<Employee> employeeList =  session.createQuery("select e from Employee e", Employee.class).getResultList();
        session.close();

        return employeeList;
    }

    @Override
    public Optional<Employee> getEmployee(Long id) {
        Session session = sessionFactory.openSession();
        Employee employee = session.get(Employee.class, id);
        session.close();

        return Optional.ofNullable(employee);
    }

    @Override
    public Employee createEmployee(Employee employee, Department department) {
        Session session = sessionFactory.openSession();;
        try {
            session.beginTransaction();

            Department departmentConnected = session.merge(department);
            employee.setDepartment(departmentConnected);
            session.persist(employee);
            departmentConnected.getEmployees().add(employee);
            session.merge(departmentConnected);

            session.getTransaction().commit();
            session.close();

            return employee;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Employee employeeNewLastName = session.merge(employee);
            session.getTransaction().commit();
            session.close();

            return employeeNewLastName;
        }catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public Employee deleteEmployee(Employee employee) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.remove(employee);
            session.getTransaction().commit();
            session.close();

            return employee;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public boolean getEmployeeByFullName(String firstName, String lastName) {
        Session session = sessionFactory.openSession();
        List<Employee> employeeList = session.createQuery("from Employee e where e.firstName = :firstName" +
            " and e.lastName = :lastName", Employee.class)
            .setParameter("firstName", firstName)
            .setParameter("lastName", lastName)
            .getResultList();
        session.close();

        return employeeList.isEmpty();
    }

    @Override
    public Set<Employee> getAllEmployeeByDepartment(Long id) {
        Session session = sessionFactory.openSession();
        Department department = session.createQuery("select d from Department d join fetch" +
            " d.employees where d.id = :id", Department.class)
            .setParameter("id", id).uniqueResult();
        Set<Employee> employeeSet = department.getEmployees();
        session.close();

        return employeeSet;
    }

    @Override
    public Optional<PersonalCard> getEmployeePersonalCard(Long id) {
        Session session = sessionFactory.openSession();
        Employee employee = session.createQuery("select e from Employee e join fetch" +
                " e.personalCard where e.id = :id", Employee.class)
            .setParameter("id", id).uniqueResult();
        PersonalCard personalCard = employee.getPersonalCard();
        session.close();

        return Optional.ofNullable(personalCard);
    }

    @Override
    public Set<Project> getAllProjectsByEmployee(Long id) {
        Session session = sessionFactory.openSession();
        Employee employee = session.createQuery("select e from Employee e join fetch e.projects where e.id = :id", Employee.class)
            .setParameter("id", id).uniqueResult();
        Set<Project> projectSet = employee.getProjects();
        session.close();

        return projectSet;
    }
}
