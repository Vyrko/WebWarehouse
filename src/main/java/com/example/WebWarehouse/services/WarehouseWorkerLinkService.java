package com.example.WebWarehouse.services;

import com.example.WebWarehouse.entity.User;
import com.example.WebWarehouse.entity.Warehouse;
import com.example.WebWarehouse.entity.WarehouseWorkerLink;
import com.example.WebWarehouse.repository.WarehouseWorkerLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseWorkerLinkService {
    private final WarehouseWorkerLinkRepository warehouseWorkerLinkRepository;
    public void saveAllWorker(List<WarehouseWorkerLink> warehouseWorkerLinks) {
        warehouseWorkerLinkRepository.saveAll(warehouseWorkerLinks);
    }
    public List<WarehouseWorkerLink> findWorkerLinkByUserId(Long id){
        return warehouseWorkerLinkRepository.findByWorkerId(id);
    }

    public void addAdmin(User user, Warehouse warehouse) {
        warehouseWorkerLinkRepository.save(new WarehouseWorkerLink(user,warehouse,"Администратор" + warehouse.getName()));
    }

    public void deleteByWarehouse(Long warehouseId) {
        warehouseWorkerLinkRepository.deleteWarehouseWorkerLinkByWarehouseId(warehouseId);
    }
}
