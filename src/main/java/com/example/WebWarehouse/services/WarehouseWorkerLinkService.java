package com.example.WebWarehouse.services;

import com.example.WebWarehouse.entity.WarehouseWorkerLink;
import com.example.WebWarehouse.repository.WarehouseWorkerLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseWorkerLinkService {
    private final WarehouseWorkerLinkRepository warehouseWorkerLinkRepository;
    public void saveAllWorker(List<WarehouseWorkerLink> warehouseWorkerLinks) {
        warehouseWorkerLinkRepository.saveAll(warehouseWorkerLinks);
    }
}
