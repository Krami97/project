package org.example;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.sql.SQLOutput;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class App {
    private static final EntityManagerFactory emf = JpaUtil.buildEntityManagerFactory();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        long bookID;
        Book book = null;
        do {
            System.out.println("Odaberite:");
            System.out.println("1 ispis autora i njihovih knjiga ");
            System.out.println("2 arzuriranje naslova knjige ");
            System.out.println("3 za brisanje knjige po Id");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    ispisAutoraIKnjiga();
                    break;
                case 2:
                    System.out.println("Unesitie id knjige koju zelite azurirati:");
                    bookID= scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Unestie novi naslov knjige:");
                    String newTitle = scanner.nextLine();
                    book  = vratiKnjiguPoID(bookID);
                    azuriranjeNaslovaKnjige(book,newTitle);
                    break;
                case 3:
                    System.out.println("Unesite id knjige koju zelite obirsati");
                    bookID = scanner.nextInt();
                    scanner.nextLine();
                    book = vratiKnjiguPoID(bookID);
                    deleteBook(book);

            }

        } while (choice!=0);

        JpaUtil.closeEntityManagerFactory();


    }

    public static void kriranjeEntiteta() {
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
        EntityManager em = JpaUtil.buildEntityManager();

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


        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static List<Author> vratiSveAutore() {
        EntityManager em = JpaUtil.buildEntityManager();
        try {
            em.getTransaction().begin();
            String jsql = "SELECT a FROM Author a";
            TypedQuery<Author> query = em.createQuery(jsql, Author.class);
            List<Author> authors = query.getResultList();
            return authors;

        } catch (Exception e) {

            throw new NoSuchElementException();

        }

    }

    public static void ispisAutoraIKnjiga() {
        List<Author> authors = vratiSveAutore();


        for (Author author : authors) {
            System.out.println(author.getName());
            for (Book book : author.getBooks()) {
                System.out.println(book.getTitle());
            }

        }
    }

    public static void azuriranjeNaslovaKnjige(Book book, String newTitle) {
        EntityManager em = JpaUtil.buildEntityManager();
        try {
            em.getTransaction().begin();
            String jsql = "UPDATE Book b SET b.title =:title WHERE b.id =:id";
            Query query = em.createQuery(jsql);
            query.setParameter("title", newTitle);
            query.setParameter("id", book.getId());
            int rowsAffected = query.executeUpdate();
            System.out.println("Naslov azuriran " + rowsAffected);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static Book vratiKnjiguPoID(Long bookId) {
        EntityManager em = JpaUtil.buildEntityManager();
        try{
            em.getTransaction().begin();
            Book book = em.find(Book.class,bookId);
            em.getTransaction().commit();
            System.out.println("originalni naslov"+ book.getTitle());
            return book;

        }catch (Exception e){
            if (em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        }finally {
            em.close();
        }

        return null;
    }
    public static void deleteBook(Book book){
        EntityManager em  = JpaUtil.buildEntityManager();
         try{
             em.getTransaction().begin();
             Book mergedBook = em.merge(book);
             em.remove(mergedBook);
             em.getTransaction().commit();
             System.out.println("knjiga obrisana!");

         }catch (Exception e){
             if(em.getTransaction().isActive()){
                 em.getTransaction().rollback();
             }
             e.printStackTrace();
         }finally {
             em.close();
         }
    }

}



