package org.example;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    public static final EntityManagerFactory emf = buildEntityManagerFactory();

    public static EntityManagerFactory buildEntityManagerFactory(){
        try{
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JpaExampleUnit");

            return entityManagerFactory;
        }catch (Exception e){
            e.printStackTrace();
            throw new ExceptionInInitializerError();
        }
    }

    public static EntityManager buildEntityManager(){
        try{
            return emf.createEntityManager();
        }catch (Exception e){
            e.printStackTrace();
            throw new ExceptionInInitializerError();

        }

    }
    public static void closeEntityManagerFactory(){
        emf.close();
    }


}
