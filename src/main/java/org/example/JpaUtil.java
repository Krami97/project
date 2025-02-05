package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    public static final EntityManager em = buildEntityManager();

    public static EntityManager buildEntityManager(){
        try{
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JpaExampleUnit");
            EntityManager em = entityManagerFactory.createEntityManager();
            return em;
        }catch (Exception e){
            throw new ExceptionInInitializerError();
        }
    }


}
