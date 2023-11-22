package com.example.Vending.models;

import org.springframework.data.repository.CrudRepository;
import com.example.Vending.models.Slot;

import java.util.List;

public interface SlotRepo extends CrudRepository<Slot,String> {
    List<Slot> findByAvailableTrue();
}
