package com.example.WebWarehouse.repository;

import com.example.WebWarehouse.entity.Cell;
import com.example.WebWarehouse.entity.CellProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CellProductRepository extends JpaRepository<CellProduct,Long> {
    List<CellProduct> findAllByCell_Id(Long cellId);
    CellProduct getByCell_IdAndProductId(Long cellId,Long productId);
    CellProduct getById(Long cellProductId);
}
