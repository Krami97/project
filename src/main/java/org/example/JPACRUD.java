package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class JPACRUD {

    public static Product creteProduct(String name, ProductOrder order) {

        EntityManager em = null;
        try {
            em = JPAUtil.entityManager();
            em.getTransaction().begin();
            Product product = new Product();
            product.setName(name);
            product.setOrder(order);
            em.persist(product);
            em.getTransaction().commit();
            return product;
        } catch (Exception e) {
            if (em!= null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw new NoSuchElementException();

        } finally {
            if (em!=null) {
                em.close();
            }

        }
    }

    public static ProductOrder createOrder(String orderNumber) {
            EntityManager em = null;

        try {
            em = JPAUtil.entityManager();
            em.getTransaction().begin();
            ProductOrder productOrder = new ProductOrder();
            productOrder.setOrderNumber(orderNumber);
            em.persist(productOrder);
            em.getTransaction().commit();

            return productOrder;
        } catch (Exception e) {
            if (em!= null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw new NoSuchElementException();

        } finally {
            if (em!=null) {
                em.close();
            }

        }
    }
    public static void PrintOrderANDProducts() {
        EntityManager em = null;
        try {
            em = JPAUtil.entityManager();
            em.getTransaction().begin();
            String jsql = "SELECT po FROM ProductOrder po";
            TypedQuery<ProductOrder> query = em.createQuery(jsql, ProductOrder.class);
            List<ProductOrder> orders = query.getResultList();
            em.getTransaction().commit();


            for (ProductOrder order : orders) {
                printOrderProducts(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (em!=null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }finally{
            if(em!=null){
                em.close();
            }
        }

    }
    public static void printOrderProducts(ProductOrder order){

        for (Product product : order.getProducts()) {
            System.out.println("Narudba: " + order.getOrederNumber()+
                    " NarudbaID: "+ order.getId()+
                    " Proizvod: " + product.getName()+
                    " PorizvodID: " + product.getId());
        }

    }
    public static void CreateOrderWithProducts() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Unestite jedinstveni broj narudbe");
        String orderNumber = scanner.nextLine();
        ProductOrder productOrder = JPACRUD.createOrder(orderNumber);

        String productName;
        do {
            System.out.println("Unestie ime proizvoda koji zeliete dodati u narudbu " + orderNumber);
            System.out.println("Unestie 0 kada nezelete dodati vise prozivoda");
            productName = scanner.nextLine();
            if (!productName.equals("0")) {
                Product product = JPACRUD.creteProduct(productName, productOrder);


            }

        } while (!productName.equals("0"));
        System.out.println("Narudba i proizvodi kreirani");
    }
    public static ProductOrder GetProductOrderWithProducts(String orderNumber){
        EntityManager em = null;
        try {
            em = JPAUtil.entityManager();
            em.getTransaction().begin();
            String hql = "SELECT p FROM ProductOrder p LEFT JOIN FETCH p.products WHERE p.orderNumber =:orderNumber";
            TypedQuery<ProductOrder> query = em.createQuery(hql,ProductOrder.class);
            query.setParameter("orderNumber",orderNumber);

            ProductOrder order = query.getSingleResult();

            em.getTransaction().commit();
            return  order;
        }catch (Exception e){
            e.printStackTrace();
            if(em!= null && em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
            throw new NoSuchElementException("There is no such order");
        }finally{
            if(em!= null){
                em.close();
            }
        }
    }
    public static void DeleteProductFromOrder(long orderID, long productID){
        EntityManager em = null;
        try {
            em = JPAUtil.entityManager();
            em.getTransaction().begin();
            String jsql = "DELETE  FROM Product p WHERE  p.productOrder.id=:orderID AND p.id=:id";
            Query query =  em.createQuery(jsql);
            query.setParameter("orderID",orderID);
            query.setParameter("id",productID);
            query.executeUpdate();

            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            if(em != null && em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
        }finally {
            if(em!=null){
                em.close();
            }
        }
    }
    public static void deleteOrderAndProducts(String orderNumber){
        EntityManager em = null;
        ProductOrder productOrder = GetProductOrderWithProducts(orderNumber);
        try {
            em = JPAUtil.entityManager();
            em.getTransaction().begin();
            ProductOrder mergedProductOrder =em.merge(productOrder);
            em.remove(mergedProductOrder);

            em.getTransaction().commit();
        }catch (Exception e){
            e.printStackTrace();
            if(em != null && em.getTransaction().isActive()){
                em.getTransaction().rollback();
            }
        }finally {
            if(em != null){
                em.close();
            }
        }

    }
}
