package com.example.Vending.models;
import javax.persistence.*;
import java.util.List;

@Entity
public class VendingMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "vendingMachine")
    private List<Slot> slots;

    public VendingMachine(Long id, List<Slot> slots) {
        this.id = id;
        this.slots = slots;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }
}
