package com.example.Vending.services;

import com.example.Vending.models.Slot;
import com.example.Vending.repositories.SlotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}