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
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Department departmentConnected = session.merge(department);
        employee.setDepartment(departmentConnected);
        session.persist(employee);
        departmentConnected.getEmployees().add(employee);
        session.merge(departmentConnected);

        session.getTransaction().commit();
        session.close();

        return employee;
    }

    @Override
    public Employee updateEmployee(UpdateEmployeeDto updateEmployeeDto, Employee employee) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Employee employeeNewLastName = session.merge(employee);

        session.getTransaction().commit();
        session.close();

        return employeeNewLastName;
    }

    @Override
    public Employee deleteEmployee(Employee employee) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        session.remove(employee);

        session.getTransaction().commit();
        session.close();

        return  employee;
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
    public Set<Employee> getAllEmployeeByDepartment(Department department) {
        Session session = sessionFactory.openSession();

        Department departmentConnected = session.merge(department);
        Set<Employee> employeeSet = departmentConnected.getEmployees();
        System.out.println(employeeSet);

        session.close();

        return employeeSet;
    }

    @Override
    public Optional<PersonalCard> getEmployeePersonalCard(Employee employee) {
        Session session = sessionFactory.openSession();

        Employee employeeConnected = session.merge(employee);
        PersonalCard personalCard = employeeConnected.getPersonalCard();

        session.close();

        return Optional.ofNullable(personalCard);
    }

    @Override
    public Set<Project> getAllProjectsByEmployee(Employee employee) {
        Session session = sessionFactory.openSession();

        Employee employeeConnected = session.merge(employee);
        Set<Project> projectSet = employeeConnected.getProjects();

        session.close();

        return projectSet;
    }
}
