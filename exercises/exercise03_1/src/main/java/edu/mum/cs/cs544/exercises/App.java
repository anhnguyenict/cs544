package edu.mum.cs.cs544.exercises;

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
        createObjects();
        print();

        closeSessionFactory();
    }

    private static void closeSessionFactory() {
        // Close the SessionFactory (not mandatory)
        sessionFactory.close();
        System.exit(0);
    }

    private static void createObjects() {
        // Hibernate placeholders
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            Owner owner1 = new Owner("Phan Anh Nguyen 1", "1000 N 4th St");
            session.persist(owner1);
            Owner owner2 = new Owner("Phan Anh Nguyen 2", "1000 N 4th St");
            session.persist(owner2);

            Car car1 = new Car("BMW", "SDA231", 30221.00);
            // owner can be null
            car1.setOwner(owner1);
            session.persist(car1);
            Car car2 = new Car("Mercedes", "HOO100", 4088.00);
            car2.setOwner(owner2);
            session.persist(car2);

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

    private static void print() {
        // Hibernate placeholders
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            @SuppressWarnings("unchecked")
            List<Car> carList = session.createQuery("from Car").list();
            for (Car car : carList) {
                System.out.println("id = " + car.getId() + ", brand = " + car.getBrand() + ", year= " + car.getYear()
                        + ", price= " + car.getPrice() + ", owner name = "
                        + (car.getOwner() != null ? car.getOwner().getName() : "") + ", owner address = "
                        + (car.getOwner() != null ? car.getOwner().getAddress() : ""));
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
