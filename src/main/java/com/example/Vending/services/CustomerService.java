package com.example.Vending.services;

import com.example.Vending.models.Product;
import com.example.Vending.models.Slot;
import com.example.Vending.models.VendingMachine;
import com.example.Vending.models.SlotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final SlotRepo slotRepo;

    @Autowired
    public CustomerService(SlotRepo slotRepo) {
        this.slotRepo = slotRepo;
    }

    public List<Product> listAvailableProducts() {
        List<Slot> availableSlots = slotRepo.findByAvailableTrue();
        return availableSlots.stream()
                .map(Slot::getProduct)
                .toList();
    }

    public void purchaseProduct(String slotId) {
        Optional<Slot> slotOptional = slotRepo.findById(slotId);

        if (slotOptional.isPresent()) {
            Slot slot = slotOptional.get();

            if (slot.isAvailable() || slot.getProduct() == null) {
                System.out.println("Slot is empty or product is not available in the specified slot.");
            } else {
                Product product = slot.getProduct();
                System.out.println("Product purchased: " + product.getName());

                // Update slot status after purchase
                slot.setProduct(null);
                slot.setAvailable(true);
                slotRepo.save(slot);
            }
        } else {
            System.out.println("Slot with ID " + slotId + " not found.");
        }
    }
}
