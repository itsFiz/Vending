package com.example.Vending.services;
import com.example.Vending.models.Product;
import com.example.Vending.models.Slot;
import com.example.Vending.models.ProductRepo;
import com.example.Vending.models.SlotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
// OwnerService.java
@Service
public class OwnerService {

    private final ProductRepo productRepo;
    private final SlotRepo slotRepo;

    @Autowired
    public OwnerService(ProductRepo productRepo, SlotRepo slotRepo) {

        this.productRepo = productRepo;
        this.slotRepo = slotRepo;
    }

    @PostConstruct
    public void initializeSlots() {
        System.out.println("Initializing slots");

        // Initialize 10 small slots
        for (int i = 1; i <= 10; i++) {
            slotRepo.save(new Slot("s" + i, null));
        }

        // Initialize 10 medium slots
        for (int i = 1; i <= 10; i++) {
            slotRepo.save(new Slot("m" + i, null));
        }
    }

    public String loadProduct(String productName, String productType) {
        // Create a new product
        Product newProduct = new Product();
        newProduct.setName(productName);
        newProduct.setType(productType);

        // Save the product to the database
        productRepo.save(newProduct);

        // Find an available slot to assign the product
        Optional<Slot> availableSlot = findAvailableSlot(productType);

        if (availableSlot.isPresent()) {
            // Assign the product to the available slot
            Slot slot = availableSlot.get();
            slot.setProduct(newProduct);

            // Save the updated slot to the database
            slotRepo.save(slot);

            return "Product " + slot.getProduct().getId()    +
                    " - " + productName + " " + productType + " loaded successfully into " + slot.getItemCode();
        } else {
            return "No available slots for the product type: " + productType;
        }
    }
    private Optional<Slot> findAvailableSlot(String productType) {
        List<Slot> slots;
        Optional<Slot> availableSlot;

        if (productType.equals("chip")) {
            // If the product is a chip, look for available medium slots
            slots = slotRepo.findAllByItemCodeStartingWith("m");
        } else {
            // If the product is not a chip (e.g., "cookie"), look for available small slots first
            slots = slotRepo.findAllByItemCodeStartingWith("s");

            // If no available small slot, try to find an available medium slot
            if (slots.isEmpty()) {
                slots = slotRepo.findAllByItemCodeStartingWith("m");
            }
        }

        System.out.println("Slots for " + productType + ":");
        for (Slot slot : slots) {
            System.out.println("Slot ID: " + slot.getId() + ", Item Code: " + slot.getItemCode() + ", Product ID: " + (slot.getProduct() != null ? slot.getProduct().getId() : "null"));
        }

        availableSlot = slots.stream()
                .filter(slot -> slot.getProduct() == null)
                .findFirst();

        return availableSlot;
    }

}