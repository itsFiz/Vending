package com.example.Vending.models;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Vending.models.Slot;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlotRepo extends JpaRepository<Slot, String> {
    List<Slot> findAllByItemCodeStartingWith(String itemCode);


}



