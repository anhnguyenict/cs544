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

//            Product p1 = new Product("Product 1", "Product 1");
//            Product p2 = new Product("Product 2", "Product 2");
            Product p1 = new CD("Artist 1");
            p1.setName("Product 1");
            Product p2 = new DVD("Genre 1");
            p2.setName("Product 2");
            Product p3 = new Book("Title 1");
            p3.setName("Product 3");

            Customer c1 = new Customer("First name 1", "Last name 1");
            Customer c2 = new Customer("First name 2", "Last name 2");

            OrderLine ol1 = new OrderLine(2, p1);
            OrderLine ol2 = new OrderLine(4, p2);
            OrderLine ol3 = new OrderLine(6, p3);

            Order o1 = new Order(new Date(), c1);
            Order o2 = new Order(new Date(), c1);
            Order o3 = new Order(new Date(), c2);

            ArrayList<OrderLine> oll1 = new ArrayList<OrderLine>();
            oll1.add(ol1);
            o1.setOrderLines(oll1);
            ArrayList<OrderLine> oll2 = new ArrayList<OrderLine>();
            oll2.add(ol2);
            o2.setOrderLines(oll2);
            ArrayList<OrderLine> oll3 = new ArrayList<OrderLine>();
            oll3.add(ol3);
            o3.setOrderLines(oll3);

            session.persist(p1);
            session.persist(p2);
            session.persist(p3);
            session.persist(c1);
            session.persist(c2);
            session.persist(ol1);
            session.persist(ol2);
            session.persist(ol3);
            session.persist(o1);
            session.persist(o2);
            session.persist(o3);

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

            List<Customer> customerList = session.createQuery("from Customer").list();
            for (Customer customer : customerList) {
                System.out.println("customer id = " + customer.getId() + ", firstname = " + customer.getFirstname()
                        + ", lastname = " + customer.getLastname());
                List<Order> orderList = customer.getOrders();
                for (Order order : orderList) {
                    System.out.println("order id = " + order.getOrderid());
                    List<OrderLine> orderLineList = order.getOrderLines();
                    for (OrderLine orderLine : orderLineList) {
                        System.out.println(
                                "order line id = " + orderLine.getId() + ", quantity = " + orderLine.getQuantity());
                        System.out.println("product id = " + orderLine.getProduct().getId() + ", product name = "
                                + orderLine.getProduct().getName());
                        System.out.println("product type = " + orderLine.getProduct().getProductType());
                    }
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
