package com.example.WebWarehouse.repository;

import com.example.WebWarehouse.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
