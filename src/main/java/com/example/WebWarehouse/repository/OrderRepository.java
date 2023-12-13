package com.example.WebWarehouse.repository;

import com.example.WebWarehouse.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByBuyer_Id(Long id);
    List<Order> findByProduct_Id(Long id);
    List<Order> findBySupplier_Id(Long id);
    void deleteByProductId(Long id);
}
