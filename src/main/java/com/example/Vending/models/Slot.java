package com.example.Vending.models;

// Slot.java

import javax.persistence.*;
@Entity
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; // e.g., 1, 2, 3

    private String itemCode; // e.g., s1, s2, m1, m2

    @OneToOne
    private Product product;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_type")
    private String productType;


    // Constructors, getters, and setters...


    public Slot() {
    }

    public Slot( String itemCode, Product product, String productName, String productType) {

        this.itemCode = itemCode;
        this.product = product;
        this.productName = productName;
        this.productType = productType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
// Getters and setters...


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
