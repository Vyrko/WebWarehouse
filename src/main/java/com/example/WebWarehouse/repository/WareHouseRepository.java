package com.example.WebWarehouse.repository;

import com.example.WebWarehouse.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WareHouseRepository extends JpaRepository<Warehouse,Long> {
    Optional<Warehouse> findById(Long id);
    List<Warehouse> findAllByUserId(Long id);
    void deleteById(Long id);

}
