package edu.mum.cs.cs544.exercises;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public static void main(String[] args) throws ParseException {
        // a
        aCreateObjects();
        aPrint();

        // b
        bCreateObjects();
        bPrint();

        // c
        cCreateObjects();
        cPrint();

        // d
        dCreateObjects();
        dPrint();

        // e
        eCreateObjects();
        ePrint();

        // e
        fCreateObjects();
        fPrint();

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

    private static void dCreateObjects() throws ParseException {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            Customer c1 = new Customer("Customer 1");
            Customer c2 = new Customer("Customer 2");

            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
            Reservation r1 = new Reservation(df.parse("05/18/2020"));
            Reservation r2 = new Reservation(df.parse("05/19/2020"));
            Reservation r3 = new Reservation(df.parse("05/20/2020"));

            ArrayList<Reservation> rl1 = new ArrayList<Reservation>();
            rl1.add(r1);
            rl1.add(r2);
            c1.setReservation(rl1);

            ArrayList<Reservation> rl2 = new ArrayList<Reservation>();
            rl2.add(r3);
            c2.setReservation(rl2);

            session.persist(c1); // execute insert query
            session.persist(c2); // execute insert query
            session.persist(r1); // execute 1 insert query + 1 update
            session.persist(r2); // execute 1 insert query + 1 update
            session.persist(r3); // execute 1 insert query + 1 update

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

    private static void dPrint() {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
            List<Customer> customerList = session.createQuery("from Customer").list(); // execute 1 select query
            for (Customer c : customerList) {
                System.out.println("Customer name = " + c.getName());
                List<Reservation> reservation = c.getReservation(); // execute 1 select query
                for (Reservation r : reservation) {
                    System.out.println("Reservation date = " + df.format(r.getDate()));
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

    private static void eCreateObjects() throws ParseException {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            Book b1 = (Book) session.get(Book.class, "B001");
            Reservation r1 = (Reservation) session.get(Reservation.class, 1);
            r1.setBook(b1);
            Reservation r2 = (Reservation) session.get(Reservation.class, 2);
            r2.setBook(b1);
            Book b3 = (Book) session.get(Book.class, "B003");
            Reservation r3 = (Reservation) session.get(Reservation.class, 3);
            r3.setBook(b3);

            session.persist(r1); // execute 1 insert query + 1 update
            session.persist(r2); // execute 1 insert query + 1 update
            session.persist(r3); // execute 1 insert query + 1 update

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

    private static void ePrint() {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
            List<Reservation> reservationList = session.createQuery("from Reservation").list(); // execute 1 select
                                                                                                // query
            for (Reservation r : reservationList) {
                System.out.println("Reservation id = " + r.getId() + ", Reservation date = " + df.format(r.getDate())
                        + ", Book ISBN = " + r.getBook().getIsbn());
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

    private static void fCreateObjects() throws ParseException {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            Office o1 = new Office(1, "Office 1");
            Employee e1 = (Employee) session.get(Employee.class, 1);
            Employee e2 = (Employee) session.get(Employee.class, 2);
//            List<Employee> el1 = new ArrayList<Employee>();
//            el1.add(e1);
//            el1.add(e2);
//            o1.setEmployees(el1);
            e1.setOffice(o1);
            e2.setOffice(o1);

            session.persist(o1); // execute 1 insert query
            session.persist(e1); // execute 1 select query + 1 update query
            session.persist(e2); // execute 1 insert query + 1 update query

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

    private static void fPrint() {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            List<Employee> employeeList = session.createQuery("from Employee").list(); // execute 1 select query
            for (Employee e : employeeList) {
                System.out.println("Employee name = " + e.getName() + ", Department name = "
                        + e.getDepartment().getName() + ", Office building = " + e.getOffice().getBuilding());
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
