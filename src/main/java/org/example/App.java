package org.example;
import org.hibernate.cfg.Configuration;

import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Set;


public class App
{
    public static void main( String[] args )
    {
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();


        Session session = sessionFactory.openSession();


        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            // Kreiranje novog objekta koji želimo spremiti
            Autor autor1 = new Autor();
            Book book1 = new Book();
            Publisher publisher1= new Publisher();

            Book book2 = new Book();
            Publisher publisher2= new Publisher();

            autor1.setName("pero");
            book1.setAutor(autor1);
            book1.setTitle("perina prica");
            book2.setAutor(autor1);
            book2.setTitle("perina prica 2");
            publisher1.setName("marko");
            publisher2.setName("niko");
            publisher1.setAutors(autor1);
            publisher2.setAutors(autor1);
            autor1.setPublishers(publisher1);
            autor1.setPublishers(publisher2);

            // Spremanje objekta u bazu podataka
            session.save(publisher1);
            session.save(publisher2);
            session.save(book1);
            session.save(book2);
            session.save(autor1);

            // Završetak transakcije
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // Zatvaranje sesije
            session.close();
        }

        // Zatvaranje SessionFactory
        sessionFactory.close();
    }



    }



