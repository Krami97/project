package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HibernateCRUD {

    /**
     * Crete product for specific ProductOrder
     * @param name of product
     * @param order where product is added
     * @return  Product
     */
    public static Product creteProduct(String name, ProductOrder order) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.createHibernateSession();
            transaction = session.beginTransaction();
            Product product = new Product();
            product.setName(name);
            product.setOrder(order);
            session.persist(product);
            transaction.commit();
            return product;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new NoSuchElementException();

        } finally {
            if (session != null) {
                session.close();
            }

        }
    }

    public static ProductOrder createOrder(String orderNumber) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.createHibernateSession()) {
            transaction = session.beginTransaction();
            ProductOrder productOrder = new ProductOrder();
            productOrder.setOrderNumber(orderNumber);
            session.save(productOrder);
            transaction.commit();
            return productOrder;
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            throw new NoSuchElementException();
        }
    }


    public static void PrintOrderANDProducts() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.createHibernateSession()) {

            transaction = session.beginTransaction();
            String hql = "SELECT po FROM ProductOrder po";
            Query<ProductOrder> query = session.createQuery(hql, ProductOrder.class);
            List<ProductOrder> orders = query.getResultList();
            transaction.commit();


            for (ProductOrder order : orders) {
                printOrderProducts(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }

    }

    public static void CreateOrderWithProducts() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Unestite jedinstveni broj narudbe");
        String orderNumber = scanner.nextLine();
        ProductOrder productOrder = HibernateCRUD.createOrder(orderNumber);

        String productName;
        do {
            System.out.println("Unestie ime proizvoda koji zeliete dodati u narudbu " + orderNumber);
            System.out.println("Unestie 0 kada nezelete dodati vise prozivoda");
            productName = scanner.nextLine();
            if (!productName.equals("0")) {
                Product product = HibernateCRUD.creteProduct(productName, productOrder);

            }

        } while (!productName.equals("0"));
        System.out.println("Narudba i proizvodi kreirani");
    }
    public static ProductOrder GetProductOrderWithProducts(String orderNumber){
        Transaction transaction = null;
        try(Session session = HibernateUtil.createHibernateSession()){
            transaction = session.beginTransaction();
            String hql = "SELECT p FROM ProductOrder p LEFT JOIN FETCH p.products WHERE p.orderNumber =:orderNumber";
            Query<ProductOrder> query = session.createQuery(hql,ProductOrder.class);
            query.setParameter("orderNumber",orderNumber);

            ProductOrder order = query.getSingleResult();

            transaction.commit();
            return  order;
        }catch (Exception e){
            e.printStackTrace();
            if(transaction!=null){
                transaction.rollback();
            }
            throw new NoSuchElementException("There is no such order");
        }
    }
    public static void DeleteProductFromOrder(long orderID, long productID){
        Transaction transaction = null;
        try(Session session = HibernateUtil.createHibernateSession()){
            transaction =session.beginTransaction();
            String hql = "DELETE FROM Product p WHERE  p.productOrder.id=:orderID AND p.id=:id";
            Query query = session.createQuery(hql);
            query.setParameter("orderID",orderID);
            query.setParameter("id",productID);
            query.executeUpdate();

            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            if(transaction != null){
                transaction.rollback();
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
    public static void deleteOrderAndProducts(String orderNumber){
        Transaction transaction = null;
        ProductOrder productOrder = GetProductOrderWithProducts(orderNumber);
        try(Session session = HibernateUtil.createHibernateSession()){
            transaction = session.beginTransaction();
            session.remove(productOrder);

            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            if(transaction != null){
                transaction.rollback();
            }
        }

    }
}
