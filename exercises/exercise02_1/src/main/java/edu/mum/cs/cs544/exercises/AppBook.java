package edu.mum.cs.cs544.exercises;

import java.util.Date;
import java.util.List;

import javax.swing.text.DateFormatter;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class AppBook {
    private static final SessionFactory sessionFactory;
    private static final ServiceRegistry serviceRegistry;

    static {
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static void main(String[] args) {
        createBooks();
        retrieveAllBooks();
        updateBook();
        retrieveAllBooks();

        closeSessionFactory();
    }

    private static void closeSessionFactory() {
        // Close the SessionFactory (not mandatory)
        sessionFactory.close();
        System.exit(0);
    }

    private static void createBooks() {
        // Hibernate placeholders
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Create new instance of Book and set values in it
            Book book1 = new Book("Book 1", "B001", "Phan Anh Nguyen", 10.5, new Date(1579910400));
            // save the car
            session.persist(book1);
            // Create new instance of Book and set values in it
            Book book2 = new Book("Book 2", "B002", "Phan Anh Nguyen", 20.5, new Date(1579996800));
            // save the car
            session.persist(book2);
            // Create new instance of Book and set values in it
            Book book3 = new Book("Book 3", "B003", "Phan Anh Nguyen", 30.5, new Date(1580083200));
            // save the car
            session.persist(book3);

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

    public static void retrieveAllBooks() {
        // Hibernate placeholders
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // retrieve all books
            @SuppressWarnings("unchecked")
            List<Book> bookList = session.createQuery("from Book").list();
            for (Book book : bookList) {
                System.out.println("title= " + book.getTitle() + ", ISBN= " + book.getISBN() + ", author= "
                        + book.getAuthor() + ", price= " + book.getPrice() + ", publish date= "
                        + book.getPublish_date().toString());
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

    public static void updateBook() {
        // Hibernate placeholders
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // retrieve and update book 1
            @SuppressWarnings("unchecked")
            Query query = session.createQuery("from Book where ISBN = :ISBN");
            query.setParameter("ISBN", "B001");
            List<Book> bookList = query.list();

//            List<Book> bookList = session.createQuery("from Book where ISBN = 'B001'").list();

            Book book1 = bookList.get(0);
            book1.setTitle("Updated Book 1");
            book1.setPrice(40.5);
            session.persist(book1);

            // retrieve and delete a book 2
            query.setParameter("ISBN", "B002");
            bookList = query.list();
            Book book2 = bookList.get(0);
            session.delete(book2);

            // try other ways to delete book 3
            // Delete a persistent object
//            Book book3 = (Book) session.get(Book.class, 3);
//            if(book3!=null){
//                session.delete(book3);
//             }

            // Delete a transient object
//            Book book3 = new Book();
//            book3.setId(3);
//            session.delete(book3);

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
