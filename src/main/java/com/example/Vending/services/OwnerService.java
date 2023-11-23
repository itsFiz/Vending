package com.example.Vending.services;
import com.example.Vending.models.Product;
import com.example.Vending.models.Slot;
import com.example.Vending.repositories.ProductRepo;
import com.example.Vending.repositories.SlotRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
            slotRepo.save(new Slot("s" + i, null, null, null));
        }

        // Initialize 10 medium slots
        for (int i = 1; i <= 10; i++) {
            slotRepo.save(new Slot("m" + i, null, null, null));
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
            slot.setProductName(productName);
            slot.setProductType(productType);

            // Save the updated slot to the database
            slotRepo.save(slot);

            return "Product " + slot.getProduct().getId() +
                    " - " + productName + " " + productType + " loaded successfully into " + slot.getItemCode();
        } else {
            return "No available slots for the product type: " + productType;
        }
    }

    private Optional<Slot> findAvailableSlot(String productType) {
        // Check if the productType is valid (chip or cookie)
        if (!productType.equals("chip") && !productType.equals("cookie")) {
            System.out.println("Invalid productType");
            return Optional.empty();
        }

        List<Slot> slots = slotRepo.findAll();

        // Find the first available slot matching the productType
        Optional<Slot> availableSlot = slots.stream()
                .filter(slot -> {
                    String itemCode = slot.getItemCode();
                    boolean isMatched = itemCode != null &&
                            ((productType.equals("cookie") && itemCode.startsWith("s")) ||
                                    (productType.equals("chip") && itemCode.startsWith("m"))) &&
                            slot.getProduct() == null &&
                            slot.getProductName() == null &&
                            slot.getProductType() == null;  // Check if the slot is not occupied

                    if (isMatched) {
                        System.out.println("Found available slot: " + slot.getId() + ", Item Code: " + itemCode);
                    } else {
                        System.out.println("Slot not available: " + slot.getId() + ", Item Code: " + itemCode);
                    }

                    return isMatched;
                })
                .findFirst();

        if (availableSlot.isPresent()) {
            return availableSlot;
        }

        // If no available slot is found, try to find a slot in the other category (m for cookie, s for chip)
        Optional<Slot> alternateSlot = slots.stream()
                .filter(slot -> {
                    String itemCode = slot.getItemCode();
                    boolean isMatched = itemCode != null &&
                            ((productType.equals("cookie") && itemCode.startsWith("m")) ) &&
                            slot.getProduct() == null &&
                            slot.getProductName() == null &&
                            slot.getProductType() == null;  // Check if the slot is not occupied

                    if (isMatched) {
                        System.out.println("Found alternate slot: " + slot.getId() + ", Item Code: " + itemCode);
                    } else {
                        System.out.println("Alternate slot not available: " + slot.getId() + ", Item Code: " + itemCode);
                    }

                    return isMatched;
                })
                .findFirst();

        return alternateSlot;
    }
}