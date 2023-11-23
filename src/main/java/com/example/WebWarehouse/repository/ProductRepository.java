package com.example.WebWarehouse.repository;

import com.example.WebWarehouse.entity.Product;
import com.example.WebWarehouse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByUserId(Long id);
    void deleteById(Long id);
}
