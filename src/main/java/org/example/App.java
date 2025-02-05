package org.example;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;


public class App
{
    private static final EntityManager em = JpaUtil.em;
    public static void main( String[] args )
    {
        List<Author> authors = vratiSveAutore();
        ;


    }
    public static void kriranjeEntiteta(){
        Author author1 = new Author();
        author1.setName("Pero");
        Author author2 = new Author();
        author2.setName("Marko");

        Publisher publisher1 = new Publisher();
        publisher1.setName("NarodneNovine");
        Publisher publisher2 = new Publisher();
        publisher2.setName("SkolskaKnjiga");

        Book book1 = new Book();
        book1.setTitle("Perina prica 1");
        book1.setAuthor(author1);
        book1.setPublishers(publisher1);
        Book book2 = new Book();
        book2.setTitle("Perina prica 2");
        book2.setAuthor(author1);
        book2.setPublishers(publisher1);


        Book book3 = new Book();
        book3.setTitle("Markova prica 1");
        book3.setAuthor(author2);
        book3.setPublishers(publisher2);
        Book book4 = new Book();
        book4.setTitle("Markova prica 2");
        book4.setAuthor(author2);
        book4.setPublishers(publisher2);

        try {
            em.getTransaction().begin();

            em.persist(author1);
            em.persist(author2);
            em.persist(publisher1);
            em.persist(publisher2);
            em.persist(book1);
            em.persist(book2);
            em.persist(book3);
            em.persist(book4);

            em.getTransaction().commit();


        }catch (Exception e){
            if(em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }
        finally {
            em.close();
        }
    }
    public static List<Author> vratiSveAutore(){
        try{
            em.getTransaction().begin();
            String jsql = "SELECT a FROM Author a";
            TypedQuery<Author> query = em.createQuery(jsql,Author.class);
            List<Author> authors = query.getResultList();
            return authors;

        }catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }






    }



