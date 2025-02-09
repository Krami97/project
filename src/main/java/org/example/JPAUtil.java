package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class JPAUtil {
    public static EntityManagerFactory emf = entityManagerFactory();


    public static EntityManagerFactory entityManagerFactory(){
        try{
            return Persistence.createEntityManagerFactory("JpaExampleUnit");
        }catch (Exception e){
            throw new ExceptionInInitializerError(e);
        }
    }
    public static EntityManager entityManager(){
        try{
            return emf.createEntityManager();
        }catch (Exception e){
            throw new ExceptionInInitializerError(e);
        }
    }
    public static void closeEmf(){
        if(emf.isOpen()){
            emf.close();
        }
    }

    public static EntityManagerFactory getEmf() {
        return emf;
    }
}
