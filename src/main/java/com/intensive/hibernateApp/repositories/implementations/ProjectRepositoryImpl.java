package com.intensive.hibernateApp.repositories.implementations;

import com.intensive.hibernateApp.entities.Employee;
import com.intensive.hibernateApp.entities.Project;
import com.intensive.hibernateApp.repositories.interfaces.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepository {
    private final SessionFactory sessionFactory;

    @Override
    public List<Project> getAllProjects() {
        Session session = sessionFactory.openSession();
        List<Project> projectList = session.createQuery("select p from Project p", Project.class).getResultList();
        session.close();

        return projectList;
    }

    @Override
    public Optional<Project> getProject(Long id) {
        Session session = sessionFactory.openSession();;
        Project project = session.get(Project.class, id);
        session.close();

        return Optional.ofNullable(project);
    }

    @Override
    public Project createProject(Project project) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.persist(project);
            session.getTransaction().commit();
            session.close();

            return project;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public Project updateProject(Project project) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Project projectUpdated = session.merge(project);
            session.getTransaction().commit();
            session.close();

            return projectUpdated;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public Project deleteProject(Project project) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.remove(project);
            session.getTransaction().commit();
            session.close();

            return project;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public boolean getProjectByName(String name) {
        Session session = sessionFactory.openSession();
        List<Project> projectList = session.createQuery("from Project p where p.name = :name", Project.class)
            .setParameter("name", name)
            .getResultList();
        session.close();

        return projectList.isEmpty();
    }

    @Override
    public Project addEmployeeToProject(Project project, Employee employee) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Project projectConnected = session.merge(project);
            Employee employeeConnected = session.merge(employee);

            projectConnected.getEmployees().add(employeeConnected);
            employeeConnected.getProjects().add(projectConnected);
            Project projectUpdated = session.merge(projectConnected);

            session.getTransaction().commit();
            session.close();

            return projectUpdated;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    @Override
    public Set<Employee> getAllEmployeeByProject(Project project) {
        Session session = sessionFactory.openSession();
        Project projectConnected = session.merge(project);
        Set<Employee> employeeSet = projectConnected.getEmployees();
        session.close();

        return employeeSet;
    }
}
