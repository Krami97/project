package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    public static SessionFactory sf = createSessionFactory();



    /**
     * Creates SessionFactory for Hibernate
     *
     * @return SessionFactory
     */
    public static SessionFactory createSessionFactory(){
        try{
            SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            return sessionFactory;

        }catch (Exception e){
            e.printStackTrace();
            throw new ExceptionInInitializerError();
        }
    }

    /**
     * Creates Session for Hibernate
     * @return Session
     */
    public static Session createHibernateSession(){
        try{
            Session session = sf.openSession();

            return session;

        }catch (Exception e){
            e.printStackTrace();
            throw new ExceptionInInitializerError();
        }
    }

    /**
     *
     *
     * @return SessionFactory
     */
    public static SessionFactory getSf() {
        return sf;
    }

    /**
     * Close SessionFactory
     */
    public static void closeSf(){
        sf.close();
    }
}
