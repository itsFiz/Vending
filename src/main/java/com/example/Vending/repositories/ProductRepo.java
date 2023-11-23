package com.example.Vending.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.Vending.models.Product;
public interface ProductRepo extends CrudRepository<Product,Long> {
}
