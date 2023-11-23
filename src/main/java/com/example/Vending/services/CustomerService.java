package com.example.Vending.services;

import com.example.Vending.models.Customer;
import com.example.Vending.models.Product;
import com.example.Vending.models.Slot;
import com.example.Vending.repositories.SlotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final SlotRepo slotRepo;

    @Autowired
    public CustomerService(SlotRepo slotRepo) {
        this.slotRepo = slotRepo;
    }

    public List<Slot> viewSlots() {
        return slotRepo.findAll();
    }
    @Transactional
    public String purchaseProduct(Long customerId, Long productId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalCustomer.isPresent() && optionalProduct.isPresent()) {
            Customer customer = optionalCustomer.get();
            Product product = optionalProduct.get();

            // Check if the customer has enough balance
            if (customer.getBalance() >= product.getPrice()) {
                // Deduct the cost from the customer's balance
                customer.setBalance(customer.getBalance() - product.getPrice());

                // Update the purchase history
                PurchaseHistory purchaseHistory = new PurchaseHistory(customer, product);
                purchaseHistoryRepository.save(purchaseHistory);

                // Update the product quantity (if applicable) or mark it as out of stock
                // Update the slot information (if applicable) or mark it as empty

                // Save the updated entities
                customerRepository.save(customer);

                return "Purchase successful!";
            } else {
                return "Insufficient balance to purchase this product.";
            }
        } else {
            return "Customer or product not found.";
        }
    }
}