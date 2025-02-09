package org.example;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String orderNumber;


    @OneToMany(mappedBy = "productOrder" ,cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    public ProductOrder(){};

    public long getId(){
        return this.id;
    }
    public void setId(long id){
        this.id = id;
    }
    public void setOrderNumber(String orderNumber){
        this.orderNumber = orderNumber;
    }
    public String getOrederNumber(){
        return this.orderNumber;
    }
    public void setProducts(Product product){
        this.products.add(product);
    }
    public List<Product> getProducts(){
        return this.products;
    }



}
