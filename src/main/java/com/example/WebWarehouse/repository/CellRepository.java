package com.example.WebWarehouse.repository;

import com.example.WebWarehouse.entity.Cell;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CellRepository extends JpaRepository<Cell,Long> {
    List<Cell> findAllByWarehouseId(Long id);
    void deleteCellByWarehouseId(Long id);
}
