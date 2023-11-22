package com.example.Vending.controllers;

// VendingMachineController.java

import com.example.Vending.models.Product;
import com.example.Vending.services.OwnerService;
import com.example.Vending.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class VendingMachineController {

    private final OwnerService ownerService;
    private final CustomerService customerService;

    @Autowired
    public VendingMachineController(OwnerService ownerService, CustomerService customerService) {
        this.ownerService = ownerService;
        this.customerService = customerService;
    }

    // Owner endpoints



    @PostMapping("/owner/load-product")
    public ResponseEntity<String> loadProduct(@RequestBody Map<String, String> request) {
        String productName = request.get("productName");
        String productType = request.get("productType");

        try {
            String response = ownerService.loadProduct(productName, productType);
            return ResponseEntity.ok(response); // Return the response from ownerService
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error loading product");
        }
    }



    // Customer endpoints

//    @GetMapping("/customer/available-products")
//    public List<Product> listAvailableProducts() {
//        return customerService.listAvailableProducts();
//    }

//    @PostMapping("/customer/purchase")
//    public void purchaseProduct(@RequestParam String slotId) {
//        customerService.purchaseProduct(slotId);
//    }
}
