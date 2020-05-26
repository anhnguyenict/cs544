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

            Employee e1 = new Employee("Phan Anh 1", "Nguyen 1");
            Employee e2 = new Employee("Phan Anh 2", "Nguyen 2");
            Laptop l1 = new Laptop("HP", "Type 1");
            Laptop l2 = new Laptop("Apple", "Type 1");
            Laptop l3 = new Laptop("Dell", "Type 1");

            l1.setEmployee(e1);
            l2.setEmployee(e1);
            l3.setEmployee(e2);

            session.persist(e1);
            session.persist(e2);
            session.persist(l1);
            session.persist(l2);
            session.persist(l3);

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

            List<Employee> employeeList = session.createQuery("from Employee").list();
            for (Employee employee : employeeList) {
                System.out.println("id = " + employee.getId() + ", firstname = " + employee.getFirstname()
                        + ", lastname = " + employee.getLastname());
                List<Laptop> laptopList = employee.getLaptopList();
                for (Laptop laptop : laptopList) {
                    System.out.println("laptop brand = " + laptop.getBrand() + ", laptop type = " + laptop.getType());
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

    private static void bCreateObjects() throws ParseException {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
            Passenger p1 = new Passenger("Phan Anh 1");
            Passenger p2 = new Passenger("Phan Anh 2");
            Flight f1 = new Flight(1, "VN", "US", df.parse("05/18/2020"));
            Flight f2 = new Flight(2, "VN", "US", df.parse("05/19/2020"));
            Flight f3 = new Flight(3, "VN", "US", df.parse("05/20/2020"));

            ArrayList<Flight> fl1 = new ArrayList<Flight>();
            fl1.add(f1);
            fl1.add(f2);
            p1.setFlights(fl1);

            ArrayList<Flight> fl2 = new ArrayList<Flight>();
            fl2.add(f3);
            p2.setFlights(fl2);

            session.persist(p1);
            session.persist(p2);
            session.persist(f1);
            session.persist(f2);
            session.persist(f3);

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

            List<Passenger> passengerList = session.createQuery("from Passenger").list();
            for (Passenger passenger : passengerList) {
                System.out.println("id = " + passenger.getId() + ", name = " + passenger.getName());
                List<Flight> flightList = passenger.getFlights();
                for (Flight flight : flightList) {
                    System.out.println("flightnumber = " + flight.getFlightnumber() + ", from = " + flight.getFrom()
                            + ", to = " + flight.getTo());
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
