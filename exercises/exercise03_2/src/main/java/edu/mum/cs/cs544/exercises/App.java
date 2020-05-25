package edu.mum.cs.cs544.exercises;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class App {
    private static final SessionFactory sessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static void main(String[] args) {
        // a
        aCreateObjects();
        aPrint();

        // b
        bCreateObjects();
        bPrint();

        // c
        cCreateObjects();
        cPrint();

        closeSessionFactory();
    }

    private static void closeSessionFactory() {
        // Close the SessionFactory (not mandatory)
        sessionFactory.close();
        System.exit(0);
    }

    private static void aCreateObjects() {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            Department d1 = new Department("Department 1");

            Employee e1 = new Employee("Employee 1");
            e1.setDepartment(d1);
            Employee e2 = new Employee("Employee 2");
            e2.setDepartment(d1);

            session.persist(d1); // execute insert query
            session.persist(e1); // execute insert query
            session.persist(e2); // execute insert query

            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                System.err.println("Rolling back: " + e.getMessage());
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private static void aPrint() {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            Department d1 = (Department) session.get(Department.class, 1); // execute select query
            if (d1 != null) {
                System.out.println("Deparment name = " + d1.getName());
                List<Employee> employees = d1.getEmployees(); // execute select query
                for (Employee e : employees) {
                    System.out.println("Employee name = " + e.getName());
                }
            }

            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                System.err.println("Rolling back: " + e.getMessage());
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private static void bCreateObjects() {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            Publisher p1 = new Publisher("Publisher 1");
            Publisher p2 = new Publisher("Publisher 2");
            Book b1 = new Book("B001", "Book 1", "Author 1");
            b1.setPublisher(p1);
            Book b2 = new Book("B002", "Book 2", "Author 2");
            Book b3 = new Book("B003", "Book 3", "Author 3");
            b3.setPublisher(p2);

            session.persist(p1); // execute insert query
            session.persist(p2); // execute insert query
            session.persist(b1); // execute 2 insert query (Book, book_publisher)
            session.persist(b2); // execute insert query
            session.persist(b3); // execute 2 insert query (Book, book_publisher)

            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                System.err.println("Rolling back: " + e.getMessage());
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private static void bPrint() {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            List<Book> bookList = session.createQuery("from Book").list(); // execute 1 select query
            for (Book b : bookList) {
                System.out
                        .println("ISBN = " + b.getIsbn() + ", Title = " + b.getTitle() + ", Author = " + b.getAuthor());
                if (b.getPublisher() != null) {
                    System.out.println("Publisher = " + b.getPublisher().getName()); // execute 1 select query
                }
            }

            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                System.err.println("Rolling back: " + e.getMessage());
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private static void cCreateObjects() {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            Student s1 = new Student("First 1", "Last 1");
            Student s2 = new Student("First 2", "Last 2");
            Student s3 = new Student("First 3", "Last 3");

            Course c1 = new Course(1, "Course 1");
            Course c2 = new Course(2, "Course 2");

            ArrayList<Course> cl1 = new ArrayList<Course>();
            cl1.add(c1);
            cl1.add(c2);
            s1.setCourses(cl1);

            ArrayList<Course> cl2 = new ArrayList<Course>();
            cl2.add(c1);
            s2.setCourses(cl2);

            ArrayList<Course> cl3 = new ArrayList<Course>();
            cl3.add(c2);
            s3.setCourses(cl3);

            session.persist(s1); // execute insert query
            session.persist(s2); // execute insert query
            session.persist(s3); // execute 2 insert query (Book, book_publisher)
            session.persist(c1); // execute insert query
            session.persist(c2); // execute 2 insert query (Book, book_publisher)

            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                System.err.println("Rolling back: " + e.getMessage());
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private static void cPrint() {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            List<Course> courseList = session.createQuery("from Course").list(); // execute 1 select query
            for (Course c : courseList) {
                System.out.println("Course name = " + c.getName());
                List<Student> students = c.getStudents();
                for (Student s : students) {
                    System.out.println("Student name = " + s.getFirstname() + " " + s.getLastname());
                }
            }

            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                System.err.println("Rolling back: " + e.getMessage());
                tx.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}
