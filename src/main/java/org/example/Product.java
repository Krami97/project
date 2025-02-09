package org.example;

import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    String name;

    @ManyToOne
    @JoinColumn(name = "orderID")
    private ProductOrder productOrder;

    public Product(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductOrder getOrder() {
        return productOrder;
    }

    public void setOrder(ProductOrder productOrder) {
        this.productOrder = productOrder;
    }
}
