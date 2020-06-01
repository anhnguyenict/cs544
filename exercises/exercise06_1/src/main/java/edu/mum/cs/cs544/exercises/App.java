package edu.mum.cs.cs544.exercises;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
        CreateObjects();
        Print();

        closeSessionFactory();
    }

    private static void closeSessionFactory() {
        // Close the SessionFactory (not mandatory)
        sessionFactory.close();
        System.exit(0);
    }

    private static void CreateObjects() {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            Doctor d1 = new Doctor("Eye doctor", "Frank", "Brown");
            Patient p1 = new Patient("John Doe", "100 Main Street", "23114", "Boston");
            Payment pm1 = new Payment("12/06/08", 100);
            Appointment a1 = new Appointment("15/05/08", p1, pm1, d1);

            session.persist(d1);
            session.persist(p1);
            session.persist(a1);
            // session.persist(pm1);

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

    private static void Print() {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            Appointment a1 = (Appointment) session.get(Appointment.class, 1);

            Doctor d1 = a1.getDoctor();
            System.out.println("doctor id = " + d1.getId() + ", doctor type = " + d1.getDoctortype()
                    + ", doctor name = " + d1.getFirstname() + " " + d1.getLastname());

            Patient p1 = a1.getPatient();
            System.out.println("patient id = " + p1.getId() + ", patient name = " + p1.getName() + ", patient street = "
                    + p1.getStreet() + ", patient zip = " + p1.getZip() + ", patient city = " + p1.getCity());

            Payment pm1 = a1.getPayment();
            System.out.println("payment date = " + pm1.getPaydate() + ", payment amount = " + pm1.getAmount());

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
