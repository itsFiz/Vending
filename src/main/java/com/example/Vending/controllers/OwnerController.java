package com.example.Vending.controllers;


import com.example.Vending.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;

    }

    // Owner endpoints


    @PostMapping("/load-product")
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


}


