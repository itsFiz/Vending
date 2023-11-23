package com.example.Vending.controllers;

import com.example.Vending.models.Slot;
import com.example.Vending.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")

public class CustomerController {


    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;

    }


    @GetMapping("/view-slots")
    public ResponseEntity<List<Slot>> viewSlots() {
        List<Slot> slots = customerService.viewSlots();
        return ResponseEntity.ok(slots);
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseProduct(@RequestBody Map<String, String> request) {
        String productName = request.get("productName");

        try {
            String response = customerService.purchaseProduct(productName);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error purchasing product");
        }
    }

}