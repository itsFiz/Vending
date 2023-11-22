package com.example.Vending.models;

import com.example.Vending.models.VendingMachine;
import org.springframework.data.repository.CrudRepository;

public interface VendingMachineRepo extends CrudRepository<VendingMachine, Long> {

}
