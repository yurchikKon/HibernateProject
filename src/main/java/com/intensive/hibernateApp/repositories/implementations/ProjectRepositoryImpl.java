package com.intensive.hibernateApp.repositories.implementations;

import com.intensive.hibernateApp.entities.Employee;
import com.intensive.hibernateApp.entities.Project;
import com.intensive.hibernateApp.repositories.interfaces.ProjectRepository;
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
public class ProjectRepositoryImpl implements ProjectRepository {
    private final SessionFactory sessionFactory;

    @Override
    public List<Project> getAllProjects() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select p from Project p", Project.class).getResultList();
        }
    }

    @Override
    public Optional<Project> getProject(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Project.class, id));
        }
    }

    @Override
    public Project createProject(Project project) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(project);
            transaction.commit();

            return project;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public Project updateProject(Project project) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Project projectUpdated = session.merge(project);
            transaction.commit();

            return projectUpdated;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public Project deleteProject(Project project) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(project);
            transaction.commit();

            return project;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public boolean checkIsProjectNameFree(String name) {
        try (Session session = sessionFactory.openSession()) {
            List<Project> projectList = session.createQuery("from Project p where p.name = :name", Project.class)
                .setParameter("name", name)
                .getResultList();

            return projectList.isEmpty();
        }
    }

    @Override
    public Project addEmployeeToProject(Project project, Employee employee) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Project projectConnected = session.merge(project);
            Employee employeeConnected = session.merge(employee);

            projectConnected.getEmployees().add(employeeConnected);
            employeeConnected.getProjects().add(projectConnected);
            Project projectUpdated = session.merge(projectConnected);
            transaction.commit();

            return projectUpdated;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public Set<Employee> getAllEmployeeByProject(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Project project = session.createQuery("select p from Project p join fetch p.employees e where p.id = :id", Project.class)
                .setParameter("id", id).uniqueResult();
            Set<Employee> employeeSet = project.getEmployees();

            return employeeSet;
        }
    }
}
