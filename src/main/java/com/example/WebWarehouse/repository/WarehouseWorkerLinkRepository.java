package com.example.WebWarehouse.repository;

import com.example.WebWarehouse.entity.Warehouse;
import com.example.WebWarehouse.entity.WarehouseWorkerLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WarehouseWorkerLinkRepository extends JpaRepository<WarehouseWorkerLink,Long> {
    List<WarehouseWorkerLink> findByWorkerId(Long id);
    void deleteWarehouseWorkerLinkByWarehouseId(Long id);
}
