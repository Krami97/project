package org.example;
import org.hibernate.cfg.Configuration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


public class App
{
    private static SessionFactory  sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    public static void main( String[] args )
    {
        ispisAutora();


    }
    public static void kriranjeEntitetaIVeza(){

        Session session = sessionFactory.openSession();


        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            // Kreiranje novog objekta koji želimo spremiti
            Author author1 = new Author();
            Author author2 = new Author();
            Book book1 = new Book();
            Publisher publisher1= new Publisher();

            Book book2 = new Book();
            Publisher publisher2= new Publisher();
            Book book3 = new Book();

            author1.setName("pero");
            book1.setAutor(author1);
            book1.setTitle("perina prica");
            book2.setAutor(author1);
            book2.setTitle("perina prica 2");
            publisher1.setName("marko");
            publisher2.setName("niko");
            publisher1.setAutors(author1);
            publisher2.setAutors(author1);
            author1.setPublishers(publisher1);
            author1.setPublishers(publisher2);
            author2.setName("Vlatko");
            book3.setTitle("Vlatko voli carape");
            book3.setAutor(author2);
            author2.setPublishers(publisher1);
            publisher1.setAutors(author2);


            // Spremanje objekta u bazu podataka
            session.save(author1);
            session.save(author2);
            session.save(publisher1);
            session.save(publisher2);
            session.save(book1);
            session.save(book2);
            session.save(book3);


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
    public static void ispisAutora(){
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            String hql = "SELECT a FROM Author a LEFT JOIN FETCH a.books";
            List<Author> authors = session.createQuery(hql, Author.class).list();
            for (Author author : authors) {
                System.out.println("Ime autora:");
                System.out.println(author.getName());
                System.out.println("Njegove Knjige:");
                for (Book book : author.getBooks()) {
                    System.out.println(book.getTitle());
                }
            }

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
    public static void azuriranjeNaslovaKnjige(Book book,String naslov){
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();



            Query query = session.createQuery("UPDATE Book b set b.title =:title where b.id=:id");
            query.setParameter("title",naslov);
            query.setParameter("id",book.getId());


            transaction.commit();
        }catch (Exception e){
            if(transaction != null ){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
    }
    public static void obrisiKnjigu(Book book){
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();



            Query query = session.createQuery("DELETE FROM Book b WHERE b.id =:id");
            query.setParameter("id",book.getId());



            transaction.commit();
        }catch (Exception e){
            if(transaction != null ){
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
    }




    }



