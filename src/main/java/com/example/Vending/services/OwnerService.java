package com.example.Vending.services;
import com.example.Vending.models.Product;
import com.example.Vending.models.Slot;
import com.example.Vending.models.VendingMachine;
import com.example.Vending.models.ProductRepo;
import com.example.Vending.models.SlotRepo;
import com.example.Vending.models.VendingMachineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    private final VendingMachineRepo vendingMachineRepo;
    private final ProductRepo productRepo;
    private final SlotRepo slotRepo;

    @Autowired
    public OwnerService(VendingMachineRepo vendingMachineRepo, ProductRepo productRepo, SlotRepo slotRepo) {
        this.vendingMachineRepo = vendingMachineRepo;
        this.productRepo = productRepo;
        this.slotRepo = slotRepo;
    }

    public VendingMachine getVendingMachineStatus() {
        return vendingMachineRepo.findById(1L).orElse(null);
    }

    public void loadProduct(String productName, String productType) {
        System.out.println("Trying to load product: " + productName);
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
            System.out.println("Product will be loaded into slot: " + slot.getId());
            slot.setProduct(newProduct);
            slot.setAvailable(false);

            // Save the updated slot to the database
            slotRepo.save(slot);

            System.out.println("Product loaded successfully into slot: " + slot.getId());
        } else {
            System.out.println("No available slots for the product type: " + productType);
        }
    }

    private Optional<Slot> findAvailableSlot(String productType) {
        List<Slot> slots = slotRepo.findByAvailableTrue();
        System.out.println("Available Slots: " + slots.size());

        for (Slot slot : slots) {
            System.out.println("Slot ID: " + slot.getId() + ", Item Code: " + slot.getItemCode() + ", Available: " + slot.isAvailable());
            if (slot.getItemCode() != null) {
                System.out.println("Item Code starts with 'cookie': " + slot.getItemCode().startsWith("cookie"));
                System.out.println("Item Code starts with 'chip': " + slot.getItemCode().startsWith("chip"));
            }
        }

        return slots.stream()
                .filter(slot -> slot.getItemCode() != null && slot.getItemCode().startsWith(productType))
                .findFirst();
    }
    private boolean isSlotTypeAvailable(String itemCode) {
        int slotNumber = Integer.parseInt(itemCode.substring(1)); // Extract the numeric part of the item code

        if (itemCode.startsWith("s") && slotNumber >= 1 && slotNumber <= 10) {
            return true; // Small slot
        } else if (itemCode.startsWith("m") && slotNumber >= 1 && slotNumber <= 10) {
            return true; // Medium slot
        }

        return false; // Invalid slot type or number
    }
}
