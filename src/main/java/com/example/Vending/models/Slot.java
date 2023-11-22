package com.example.Vending.models;

// Slot.java

import javax.persistence.*;

@Entity
public class Slot {
    @Id
    private String id; // e.g., s1, s2, m1, m2

    private String itemCode; // e.g., s1, s2, m1, m2
    private boolean available;

    @OneToOne
    private Product product;

    @ManyToOne
    @JoinColumn(name="vending_machine_id")
    private VendingMachine vendingMachine;

    // Constructors, getters, and setters...


    public Slot(String id, String itemCode, boolean available) {
        this.id = id;
        this.itemCode = itemCode;
        this.available = available;

    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Product getProduct() {
        return product;
    }



    public VendingMachine getVendingMachine() {
        return vendingMachine;
    }

    public void setVendingMachine(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }
}
