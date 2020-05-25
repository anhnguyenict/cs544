package cs544.exercise02_2;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import cs544.exercise02_2.Person;

public class AppPerson {

    private static final SessionFactory sessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static void main(String[] args) {
        createPeople();
        retrieveAllPeople();
        updatePerson();
        retrieveAllPeople();

        closeSessionFactory();
    }

    private static void closeSessionFactory() {
        // Close the SessionFactory (not mandatory)
        sessionFactory.close();
        System.exit(0);
    }

    private static void createPeople() {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
            Person person1 = new Person("Phan Anh 1", "Nguyen", df.parse("10/18/1990"));
            session.persist(person1);
            Person person2 = new Person("Phan Anh 2", "Nguyen", df.parse("11/18/1990"));
            session.persist(person2);
            Person person3 = new Person("Phan Anh 3", "Nguyen", df.parse("12/18/1990"));
            session.persist(person3);

            tx.commit();

        } catch (HibernateException | ParseException e) {
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

    public static void retrieveAllPeople() {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            @SuppressWarnings("unchecked")
            List<Person> personList = session.createQuery("from Person").list();
            for (Person person : personList) {
                System.out.println("first name = " + person.getFirstname() + ", last name = " + person.getLastname()
                        + ", date of birth = " + person.getDateofbirth().toString());
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

    public static void updatePerson() {
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            @SuppressWarnings("unchecked")
            Query query = session.createQuery("from Person where firstname = :firstname");
            query.setParameter("firstname", "Phan Anh 1");
            List<Person> personList = query.list();

//            List<Person> personList = session.createQuery("from Person where firstname = 'Phan Anh 1'").list();

            Person person1 = personList.get(0);
            person1.setFirstname("Updated Phan Anh 1");
            session.persist(person1);

            // retrieve and delete a book 2
            // has select query
            query.setParameter("firstname", "Phan Anh 2");
            personList = query.list();
            Person person2 = personList.get(0);
            session.delete(person2);

            // try other ways to delete book 3
            // Delete a persistent object
            // has no select query
//            Person person2 = (Person) session.get(Person.class, 2L);
//            if (person2 != null) {
//                session.delete(person2);
//            }

            // Delete a transient object
            // has no select query
//            Person person3 = new Person();
//            person3.setId(3L);
//            session.delete(person3);

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
